package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by joakimlindvall on 2017-11-10.
 */
public class ReportController {


    public void loadInventoryView(ActionEvent event){
        ViewLoader viewLoader = new ViewLoader();
        viewLoader.loadInventoryView(event);
    }

    public void loadVendorView(ActionEvent event){
        ViewLoader viewLoader = new ViewLoader();
        viewLoader.loadVendorView(event);
    }

}
