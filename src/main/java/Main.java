package main.java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main/resources/view/Inventory.fxml"));
        Scene scene = new Scene(root);

        Stage stage = primaryStage;
        stage.setTitle("Stock");
        stage.setScene(scene);
        stage.show();
    }

}
