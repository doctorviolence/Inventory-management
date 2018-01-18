package main.java.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import main.java.dao.*;
import main.java.entities.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    private BaseDaoInterface dao = new BaseDao();

    private StockDaoInterface stockDao = new StockDao();

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Number> orderIdCol;

    @FXML
    private TableColumn<Order, String> orderDateCol;

    @FXML
    private TableColumn<Order, Number> totalCostCol;

    @FXML
    private TableColumn<Order, Number> itemIdCol;

    @FXML
    private TableColumn<Order, Number> orderQuantityCol;

    @FXML
    private TableView<Stock> stockTable;

    @FXML
    private TableColumn<Stock, Number> stockIdCol;

    @FXML
    private TableColumn<Stock, String> itemNameCol;

    @FXML
    private TableColumn<Stock, Number> stockQuantityCol;

    @FXML
    private TableColumn<Stock, String> expirationDateCol;

    @FXML
    private Button refreshBtn;

    @FXML
    private Button addOrderBtn;

    @FXML
    private Button removeOrderBtn;

    @FXML
    private Text messageText;

    @FXML
    private DatePicker orderDatePicker;

    @FXML
    private DatePicker expirationDatePicker;

    @FXML
    private ComboBox vendorPicker;

    @FXML
    private TextField qtyInput;

    @FXML
    private BarChart stockChart;

    @FXML
    private ListView itemsLowInStockList;

    @FXML
    private CategoryAxis itemNameAxis;

    @FXML
    private NumberAxis qtyAxis;

    @FXML
    private Button subtractFromStockBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Order table + columns
            orderIdCol.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
            orderDateCol.setCellValueFactory(cellData -> cellData.getValue().orderDateProperty());
            itemIdCol.setCellValueFactory(cellData -> cellData.getValue().getItem().itemIdProperty());
            orderQuantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
            totalCostCol.setCellValueFactory(cellData -> cellData.getValue().totalCostProperty());
            orderTable.getItems().setAll(getOrders());

            // Stock table + columns
            stockIdCol.setCellValueFactory(cellData -> cellData.getValue().stockIdProperty());
            itemNameCol.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
            stockQuantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
            expirationDateCol.setCellValueFactory(cellData -> cellData.getValue().expirationDateProperty());
            stockTable.getItems().setAll(getItemsInStock());

            // Buttons
            addOrderBtn.setOnAction(e -> addOrder());
            removeOrderBtn.setOnAction(e -> removeOrder());
            refreshBtn.setOnAction(e -> populateChart());
            subtractFromStockBtn.setOnAction(e -> removeItemFromStock());

            // Populate list view, items low in stock
            getItemsLowInStock();

            // Stock chart
            populateChart();

            // ComboBox
            itemChoices();

            messageText.setText("Stock overview");
        } catch (Exception e) {
            e.printStackTrace();
            loadAlert(e.toString());
        }
    }

    /**
     * Add order to database
     **/
    public void addOrder() {
        String orderDate = String.valueOf(orderDatePicker.getValue());
        int qty = Integer.parseInt(qtyInput.getText());
        int itemId = Integer.parseInt(String.valueOf(vendorPicker.getValue()));
        String expirationDate = String.valueOf(expirationDatePicker.getValue());
        try {
            Item item = (Item) dao.getEntityById(Item.class, itemId);

            int cost = item.getPurchasePrice();
            int total = cost * qty;

            Order order = new Order();
            order.setOrderDate(orderDate);
            order.setQuantity(qty);
            order.setTotalCost(total);
            order.setItem(item);

            dao.createEntity(order);

            addItemToStock(qty, expirationDate, order);
            refreshOrderTable();
            messageText.setText("Order added!");
        } catch (Exception e) {
            e.printStackTrace();
            loadAlert(e.toString());
        }
    }

    /**
     * Remove order from database
     * ERROR: CANNOT DELETE OR UPDATE A PARENT ROW: FK CONSTRAINT
     **/
    public void removeOrder() {
        Order removeOrder = orderTable.getSelectionModel().getSelectedItem();
        try {
            dao.deleteEntity(removeOrder);
            refreshOrderTable();
            messageText.setText("Order removed!");
        } catch (Exception e) {
            e.printStackTrace();
            loadAlert(e.toString());
        }
    }


    /**
     * Retrieve list of orders from database
     **/
    public List<Order> getOrders() {
        List<Order> orderList = FXCollections.observableArrayList();
        orderList.addAll(dao.getEntities(Order.class));
        return orderList;
    }

    /**
     * Retrieve list of items in stock from database
     **/
    public List<Stock> getItemsInStock() {
        List<Stock> stockList = FXCollections.observableArrayList();
        stockList.addAll(stockDao.getItemsInStock());
        return stockList;
    }

    /**
     * Retrieve list of items low in stock from database
     */
    public void getItemsLowInStock() {
        List<Stock> itemsLowInStock = new ArrayList();
        List stockStatus = FXCollections.observableArrayList();
        itemsLowInStock.addAll(stockDao.getItemsShortInStock());
        for (Stock s : itemsLowInStock) {
            String items = s.getItemName() + " (" + s.getQuantity() + ")";
            stockStatus.add(items);
        }
        itemsLowInStockList.setItems((ObservableList) stockStatus);
    }

    /**
     * Add item to current stock
     **/
    public void addItemToStock(int qty, String expirationDate, Order order) {
        try {
            Stock stock = new Stock();
            stock.setQuantity(qty);
            stock.setOrder(order);
            stock.setExpirationDate(expirationDate);
            dao.createEntity(stock);
            refreshStockTable();
        } catch (Exception e) {
            e.printStackTrace();
            loadAlert(e.toString());
        }
    }

    /**
     * Remove item from stock
     */
    public void removeItemFromStock() {
        try {
            Stock stock = stockTable.getSelectionModel().getSelectedItem();
            int id = stock.getStockId();
            if (stock == null) {
                messageText.setText("Please select an item first.");
            } else {
                stockDao.removeItemFromStock(id);
                refreshStockTable();
                messageText.setText("Item removed from stock!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadAlert(e.toString());
        }
    }

    /**
     * Retrieve series, populate bar chart
     */
    public void populateChart() {
        stockChart.getData().clear();
        stockChart.getData().addAll(stockDao.getItemsInStockChart());
    }

    /**
     * Retrieve list of items from database
     **/
    public void itemChoices() {
        List<Item> items = new ArrayList();
        items.addAll(dao.getEntities(Item.class));
        for (Item i : items) {
            String item = "" + i.getItemId();
            vendorPicker.getItems().add(item);
        }
    }

    public void refreshOrderTable() {
        orderTable.getItems().clear();
        orderTable.getItems().setAll(getOrders());
    }

    public void refreshStockTable() {
        stockTable.getItems().clear();
        stockTable.getItems().setAll(getItemsInStock());
    }


    public void loadVendorView(ActionEvent event) {
        StageController stageController = new StageController();
        stageController.loadVendorView(event);

    }

    public void loadReportView(ActionEvent event) {
        StageController stageController = new StageController();
        stageController.loadReportView(event);
    }

    public void loadAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("main/resources/css/master.css");
        alert.showAndWait();
    }

    public void loadExit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exit application");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you wish to exit the application?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add("main/resources/css/master.css");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton) {
            Platform.exit();
        } else if (result.get() == noButton) {
            alert.close();
        }
    }

}
