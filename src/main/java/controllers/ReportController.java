package main.java.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import main.java.dao.*;
import main.java.entities.Item;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    private OrderDaoInterface orderDao = new OrderDao();
    private ItemDaoInterface itemDao = new ItemDao();

    @FXML
    private LineChart inventoryChart;

    @FXML
    private ListView itemList;


    public void initialize(URL location, ResourceBundle resources) {
        // Line Chart
        populateChart();

        // List view
        getTopItems();

    }

    /**
     * Set text for top 5 items, based on spending
     */
    public void getTopItems() {
        List<Item> topItems = new ArrayList();
        topItems.addAll(itemDao.getTopFiveItemsBySpending());
        List byNames = FXCollections.observableArrayList();
        int number = 0;
        for (Item i : topItems) {
            number++;
            String itemName = number + ". " + i.getItemName();
            byNames.add(itemName);
        }
        itemList.setItems((ObservableList) byNames);
    }

    /**
     * Populate line chart
     * Y-Axis: Cost
     * X-Axis: Order date
     */
    public void populateChart() {
        XYChart.Series series = new XYChart.Series();
        inventoryChart.getData().addAll(orderDao.populateLineChart());
    }

    public void loadInventoryView(ActionEvent event) {
        StageController stageController = new StageController();
        stageController.loadInventoryView(event);
    }

    public void loadVendorView(ActionEvent event) {
        StageController stageController = new StageController();
        stageController.loadVendorView(event);
    }

    public void loadCloseView(ActionEvent event) {
        StageController stageController = new StageController();
        stageController.loadCloseView(event);
    }

}
