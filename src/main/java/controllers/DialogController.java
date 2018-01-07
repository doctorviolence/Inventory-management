package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DialogController {

    @FXML
    private Label errorMessage;

    public void setErrorText(String text) {
        errorMessage.setText(text);
    }

}
