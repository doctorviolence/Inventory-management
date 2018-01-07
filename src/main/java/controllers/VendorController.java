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
import main.java.dao.GenericDao;
import main.java.entities.Vendor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by joakimlindvall on 2017-11-10.
 */
public class VendorController implements Initializable {

    private static GenericDao dao = new GenericDao();

    @FXML
    private TableView<Vendor> vendorTable = new TableView<>();

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
    private Text messageText;

    @FXML
    private Button addVendorBtn;

    @FXML
    private Button removeVendorBtn;

    @FXML
    private Button refreshBtn;

    @FXML
    private Button editVendorBtn;

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

    @Override
    public void initialize(URL location, ResourceBundle resources){
        try{
            // Table + Columns
            vendorIdCol.setCellValueFactory(cellData -> cellData.getValue().vendorIdProperty());
            vendorNameCol.setCellValueFactory(cellData -> cellData.getValue().vendorNameProperty());
            addressCol.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
            phoneCol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
            emailCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
            websiteCol.setCellValueFactory(cellData -> cellData.getValue().websiteProperty());
            vendorTable.getItems().setAll(getVendors());

            // Buttons
            addVendorBtn.setOnAction(e -> addVendor());
            removeVendorBtn.setOnAction(e -> removeVendor());
            refreshBtn.setOnAction(e -> refreshVendorTable());

            // Text
            messageText.setText("Vendor overview");
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Add vendor to database
     **/
    public void addVendor(){
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
        } catch(Exception e){
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }

    /**
     * Remove vendor from database
     **/
    public void removeVendor(){
        Vendor removeVendor = new Vendor();
        removeVendor = vendorTable.getSelectionModel().getSelectedItem();
        try {
            dao.deleteEntity(removeVendor);
            refreshVendorTable();
            messageText.setText("Vendor removed!");
        } catch(Exception e){
            messageText.setText("Error!");
            e.printStackTrace();
        }
    }

    /**
     * Refreshes vendor table by
     * 1. clearing the table
     * 2. retrieving a new list
     **/
    public void refreshVendorTable(){
        vendorTable.getItems().clear();
        vendorTable.getItems().setAll(getVendors());
    }

    /**
     * Retrieve list of vendors from database
     **/
    public ObservableList<Vendor> getVendors(){
        ObservableList<Vendor> vendorList = FXCollections.observableArrayList();
        vendorList.addAll(dao.getEntities(Vendor.class));
        return vendorList;
    }

    public void loadInventoryView(ActionEvent event){
        ViewLoader viewLoader = new ViewLoader();
        viewLoader.loadInventoryView(event);
    }

    public void loadReportView(ActionEvent event){
      ViewLoader viewLoader = new ViewLoader();
      viewLoader.loadReportView(event);
    }



}
