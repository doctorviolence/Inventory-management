package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StageController {

    private static Parent root;
    private static Scene scene;
    private static Stage stage;

    public void loadInventoryView(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/resources/view/Inventory.fxml"));
            root = (Parent) fxmlLoader.load();
            scene = new Scene(root);
            stage = new Stage();

            stage.setTitle("Inventory");
            stage.setScene(scene);
            stage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadReportView(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/resources/view/Report.fxml"));
            root = (Parent) fxmlLoader.load();
            scene = new Scene(root);
            stage = new Stage();

            stage.setTitle("Report");
            stage.setScene(scene);
            stage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadVendorView(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/resources/view/Vendor.fxml"));
            root = (Parent) fxmlLoader.load();
            scene = new Scene(root);
            stage = new Stage();

            stage.setTitle("Vendor / Item Catalog");
            stage.setScene(scene);
            stage.show();

            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadCloseView(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main/resources/view/Exit.fxml"));
            root = (Parent) fxmlLoader.load();
            scene = new Scene(root);
            stage = new Stage();

            //stage.initOwner();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
