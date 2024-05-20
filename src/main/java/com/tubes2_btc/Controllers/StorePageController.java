package com.tubes2_btc.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class StorePageController {
    @FXML
    private Button kembali;

    @FXML
    private AnchorPane main;

    @FXML
    public void initialize() {
        main.setStyle("-fx-background-color: #ADD8E6;");
    }

    @FXML
    public void handleClicked(){
            System.out.println("Kembali Clicked");
    }

}
