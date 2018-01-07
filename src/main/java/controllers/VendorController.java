package main.java.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.java.dao.*;
import main.java.entities.Catalog;

import main.java.entities.Item;
import main.java.entities.Vendor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class VendorController implements Initializable {

    private static BaseDaoInterface dao = new BaseDao();

    private static CatalogDaoInterface catalogDao = new CatalogDao();

    private static ItemDaoInterface itemDao = new ItemDao();

    @FXML
    private TableView<Vendor> vendorTable;

    @FXML
    private TableColumn<Vendor, Number> vendorIdCol;

    @FXML
    private TableColumn<Vendor, String> vendorNameCol;

    @FXML
    private TableColumn<Vendor, String> addressCol;

    @FXML
    private TableColumn<Vendor, String> phoneCol;

    @FXML
    private TableColumn<Vendor, String> emailCol;

    @FXML
    private TableColumn<Vendor, String> websiteCol;

    @FXML
    private TableView<Catalog> catalogTable;

    @FXML
    private TableColumn<Catalog, Number> catalogIdCol;

    @FXML
    private TableView<Item> itemTable;

    @FXML
    private TableColumn<Item, Number> itemIdCol;

    @FXML
    private TableColumn<Item, String> itemNameCol;

    @FXML
    private TableColumn<Item, Number> costCol;

    @FXML
    private Button addVendorBtn;

    @FXML
    private Button removeVendorBtn;

    @FXML
    private Button findCatalogsBtn;

    @FXML
    private Button addCatalogBtn;

    @FXML
    private Button removeCatalogBtn;

    @FXML
    private Button browseCatalogBtn;

    @FXML
    private Button addItemBtn;

    @FXML
    private Button removeItemBtn;

    @FXML
    private TextField vendorNameInput;

    @FXML
    private TextField addressInput;

    @FXML
    private TextField phoneInput;

    @FXML
    private TextField websiteInput;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField itemNameInput;

    @FXML
    private TextField costInput;

    @FXML
    private Text messageText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Vendor table + columns
            vendorIdCol.setCellValueFactory(cellData -> cellData.getValue().vendorIdProperty());
            vendorNameCol.setCellValueFactory(cellData -> cellData.getValue().vendorNameProperty());
            addressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
            phoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
            emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
            websiteCol.setCellValueFactory(cellData -> cellData.getValue().websiteProperty());
            vendorTable.getItems().setAll(getVendors());

            // Catalog table + columns
            catalogIdCol.setCellValueFactory(cellData -> cellData.getValue().catalogIdProperty());

            // Item table + columns
            itemIdCol.setCellValueFactory(cellData -> cellData.getValue().itemIdProperty());
            itemNameCol.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
            costCol.setCellValueFactory(cellData -> cellData.getValue().purchasePriceProperty());

            // Buttons
            addVendorBtn.setOnAction(e -> addVendor());
            removeVendorBtn.setOnAction(e -> removeVendor());
            findCatalogsBtn.setOnAction(e -> getCatalogs());
            addCatalogBtn.setOnAction(e -> addCatalogToVendor());
            removeCatalogBtn.setOnAction(e -> removeCatalogFromVendor());
            browseCatalogBtn.setOnAction(e -> getItemsFromCatalog());
            addItemBtn.setOnAction(e -> addItemToCatalog());
            removeItemBtn.setOnAction(e -> removeItemFromCatalog());

            // Text
            messageText.setText("Vendor overview");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Add vendor to database
     **/
    public void addVendor() {
        Vendor addVendor = new Vendor();
        addVendor.setVendorName(vendorNameInput.getText());
        addVendor.setAddress(addressInput.getText());
        addVendor.setPhone(phoneInput.getText());
        addVendor.setEmail(emailInput.getText());
        addVendor.setWebsite(websiteInput.getText());
        try {
            dao.createEntity(addVendor);
            refreshVendorTable();
            messageText.setText("Vendor added!");
        } catch (Exception e) {
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }

    /**
     * Remove vendor from database
     **/
    public void removeVendor() {
        Vendor removeVendor = new Vendor();
        removeVendor = vendorTable.getSelectionModel().getSelectedItem();
        try {
            dao.deleteEntity(removeVendor);
            refreshVendorTable();
            messageText.setText("Vendor removed!");
        } catch (Exception e) {
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }

    /**
     * Refreshes vendor table by
     * 1. clearing the table
     * 2. retrieving a new list
     **/
    public void refreshVendorTable() {
        vendorTable.getItems().clear();
        vendorTable.getItems().setAll(getVendors());
    }

    /**
     * Retrieve list of vendors from database
     **/
    public ObservableList<Vendor> getVendors() {
        ObservableList<Vendor> vendorList = FXCollections.observableArrayList();
        vendorList.addAll(dao.getEntities(Vendor.class));
        return vendorList;
    }


    /**
     * Retrieve catalogs from selected vendor
     */
    public void getCatalogs() {
        try {
            List<Catalog> catalogList = FXCollections.observableArrayList();
            if (vendorTable.getSelectionModel().getSelectedItem() == null) {
                messageText.setText("Please select a vendor in the vendor table first");
            } else {
                int value = getVendorId();
                catalogList.addAll(catalogDao.findCatalogsByVendor(value));

                catalogTable.getItems().clear();
                catalogTable.getItems().addAll(catalogList);
                messageText.setText("Displaying item catalogs by vendor " + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getVendorId() {
        Vendor selectedVendor = vendorTable.getSelectionModel().getSelectedItem();
        int id = selectedVendor.getVendorId();
        return id;
    }

    /**
     * Add catalog to a selected vendor in the database
     */
    public void addCatalogToVendor() {
        try {
            Catalog addCatalog = new Catalog();
            int id = getVendorId();
            Vendor toVendor = new Vendor(id);
            addCatalog.setVendor(toVendor);
            dao.createEntity(addCatalog);
            catalogTable.refresh();
            messageText.setText("Catalog added to vendor!");
        } catch (Exception e) {
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }

    /**
     * Remove catalog from a selected vendor in the database
     **/
    public void removeCatalogFromVendor() {
        Catalog removeCatalog = new Catalog();
        removeCatalog = catalogTable.getSelectionModel().getSelectedItem();
        try {
            dao.deleteEntity(removeCatalog);
            messageText.setText("Catalog removed!");
        } catch (Exception e) {
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }

    /**
     * Retrieve list of items pertaining to a particular catalog
     **/
    public void getItemsFromCatalog() {
        try {
            List<Item> itemList = FXCollections.observableArrayList();
            if (catalogTable.getSelectionModel().getSelectedItem() == null) {
                messageText.setText("Please select an catalog to the left first");
            } else {
                int value = getCatalogId();
                itemList.addAll(itemDao.findItemsInCatalog(value));

                itemTable.getItems().clear();
                itemTable.getItems().addAll(itemList);
                messageText.setText("Displaying items in catalog " + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getCatalogId() {
        Catalog selectedCatalog = catalogTable.getSelectionModel().getSelectedItem();
        int id = selectedCatalog.getCatalogId();
        return id;
    }

    /**
     * Add item to a selected order in the database
     **/
    public void addItemToCatalog() {
        try {
            Item addItem = new Item();
            addItem.setItemName(itemNameInput.getText());
            addItem.setPurchasePrice(parseInt(costInput.getText()));
            int id = getCatalogId();
            Catalog toCatalog = new Catalog(id);
            addItem.setCatalog(toCatalog);
            dao.createEntity(addItem);


            itemTable.refresh();
            messageText.setText("Item added to order!");
        } catch (Exception e) {
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }

    /**
     * Remove item from a selected order in the database
     **/
    public void removeItemFromCatalog() {
        Item removeItem = new Item();
        removeItem = itemTable.getSelectionModel().getSelectedItem();
        try {
            dao.deleteEntity(removeItem);
            messageText.setText("Item removed from catalog!");
        } catch (Exception e) {
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }

    public void loadInventoryView(ActionEvent event) {
        StageController stageController = new StageController();
        stageController.loadInventoryView(event);
    }

    public void loadReportView(ActionEvent event) {
        StageController stageController = new StageController();
        stageController.loadReportView(event);
    }

    public void loadCloseView(ActionEvent event) {
        StageController stageController = new StageController();
        stageController.loadCloseView(event);
    }


}
