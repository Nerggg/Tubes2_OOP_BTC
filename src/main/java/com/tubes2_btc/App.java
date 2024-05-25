package com.tubes2_btc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Pages/main-page.fxml"));
//      Parent root = FXMLLoader.load(getClass().getResource("Pages/load.fxml"));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Pages/Images/AddOns/logo.jpg")));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Harvest BTC Nolan");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
