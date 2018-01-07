package main.java.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class ExitController implements Initializable {

    @FXML
    private Button noButton;

    @FXML
    private Button yesButton;

    public void initialize(URL location, ResourceBundle resources) {
        noButton.setOnAction(e -> returnToApplication());
        yesButton.setOnAction(e -> closeApplication());
    }

    public void returnToApplication() {
        noButton.getScene().getWindow().hide();
    }

    public void closeApplication() {
        System.exit(0);
    }
}
