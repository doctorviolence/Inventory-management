package main.java.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.dao.GenericDao;
import main.java.entities.Item;
import main.java.entities.Order;
import main.java.entities.Vendor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

/**
 * Created by joakimlindvall on 2017-11-10.
 */
public class InventoryController implements Initializable {

    private static GenericDao dao = new GenericDao();

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Number> orderIdCol;

    @FXML
    private TableColumn<Order, String> orderDateCol;

    @FXML
    private TableColumn<Order, Number> vendorIdCol;

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TableColumn<Item, Number> itemIdCol;

    @FXML
    private TableColumn<Item, String> itemNameCol;

    @FXML
    private TableColumn<Item, String> categoryCol;

    @FXML
    private TableColumn<Item, Number> quantityCol;

    @FXML
    private TableColumn<Item, Number> purchasePriceCol;

    @FXML
    private Button itemsBtn;

    @FXML
    private Button addOrderBtn;

    @FXML
    private Button removeOrderBtn;

    @FXML
    private Button addItemBtn;

    @FXML
    private Button removeItemBtn;

    @FXML
    private Text messageText;

    @FXML
    private TextField orderIdInput;

    @FXML
    private DatePicker orderDatePicker;

    @FXML
    private ComboBox vendorPicker;

    @FXML
    private TextField itemIdInput;

    @FXML
    private TextField itemNameInput;

    @FXML
    private TextField categoryInput;

    @FXML
    private TextField qtyInput;

    @FXML
    private TextField purchasePriceInput;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            // Order table + columns
            orderIdCol.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
            orderDateCol.setCellValueFactory(cellData -> cellData.getValue().orderDateProperty());
            vendorIdCol.setCellValueFactory(cellData -> cellData.getValue().getVendor().vendorIdProperty());
            orderTable.getItems().setAll(getOrders());

            // Item table + columns
            itemIdCol.setCellValueFactory(cellData -> cellData.getValue().itemIdProperty());
            itemNameCol.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
            categoryCol.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
            quantityCol.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
            purchasePriceCol.setCellValueFactory(cellData -> cellData.getValue().purchasePriceProperty());
            //itemTable.getItems().setAll(getItemsFromOrder());

            // Buttons
            itemsBtn.setOnAction(e -> getItemsFromOrder());
            addOrderBtn.setOnAction(e -> addOrder());
            removeOrderBtn.setOnAction(e -> removeOrder());
            addItemBtn.setOnAction(e -> addItemToOrder());
            removeItemBtn.setOnAction(e -> removeItemFromOrder());
            itemsBtn.setOnAction(e -> getItemsFromOrder());

            // ComboBox
            vendorChoices();


            messageText.setText("Inventory overview");
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Add order to database
     **/
    public void addOrder(){
        Order addOrder = new Order();
        addOrder.setOrderDate(String.valueOf(orderDatePicker.getValue()));
        int id = parseInt(vendorPicker.getSelectionModel().getSelectedItem().toString());
        Vendor vendor = new Vendor(id);
        addOrder.setVendor(vendor);

        try {
            dao.createEntity(addOrder);
            refreshOrderTable();
            messageText.setText("Order added!");
        } catch(Exception e){
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }

    /**
     * Remove order from database
     **/
    public void removeOrder(){
        Order removeOrder = new Order();
        removeOrder = orderTable.getSelectionModel().getSelectedItem();
        try {
            dao.deleteEntity(removeOrder);
            refreshOrderTable();
            messageText.setText("Order removed!");
        } catch(Exception e){
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }


    /**
     * Retrieve list of orders from database
     **/
    public ObservableList<Order> getOrders(){
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        orderList.addAll(dao.getEntities(Order.class));
        return orderList;
    }

    /**
     * Retrieve list of items pertaining to a particular order
     **/
    public void getItemsFromOrder() {
        try {
            ObservableList<Item> itemList = FXCollections.observableArrayList();

            if (orderTable.getSelectionModel().getSelectedItem() == null) {
                messageText.setText("Please select an order in the order table first");
            } else {
                int value = getOrderId();
                String hsql = "FROM Item order WHERE order.order = " + value + "";
                itemList.addAll(dao.query(hsql));

                itemTable.getItems().clear();
                itemTable.getItems().addAll(itemList);
                messageText.setText("Displaying items in order " + value);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getOrderId(){
        Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        int id = selectedOrder.getOrderId();
        return id;
    }

    /**
     * Add item to a selected order in the database
     **/
    public void addItemToOrder(){
        try {
            Item addItem = new Item();
            addItem.setItemName(itemNameInput.getText());
            addItem.setCategory(categoryInput.getText());
            addItem.setQuantity(parseInt(qtyInput.getText()));
            addItem.setPurchasePrice(parseInt(purchasePriceInput.getText()));
            int id = getOrderId();
            Order toOrder = new Order(id);
            addItem.setOrder(toOrder);
            dao.createEntity(addItem);

            messageText.setText("Item added to order!");
        } catch(Exception e){
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }

    /**
     * Remove item from a selected order in the database
     **/
    public void removeItemFromOrder(){
        Item removeItem = new Item();
        removeItem = itemTable.getSelectionModel().getSelectedItem();
        try {
            dao.deleteEntity(removeItem);
            messageText.setText("Item removed!");
        } catch(Exception e){
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }

    /**
     * Retrieve list of vendors from database
     * NEED TO DO: select vendor_id as MenuItem
     **/
    public void vendorChoices(){
        List<Vendor> vendors = new ArrayList();
        vendors.addAll(dao.getEntities(Vendor.class));
        for(Vendor v : vendors){
            String vendor = "" + v.getVendorId();
            vendorPicker.getItems().add(vendor);
        }
    }

    public void refreshOrderTable(){
        orderTable.getItems().clear();
        orderTable.getItems().setAll(getOrders());
    }


    public void loadVendorView(ActionEvent event){
      ViewLoader viewLoader = new ViewLoader();
      viewLoader.loadVendorView(event);

    }

    public void loadReportView(ActionEvent event){
        ViewLoader viewLoader = new ViewLoader();
        viewLoader.loadReportView(event);
    }

}
