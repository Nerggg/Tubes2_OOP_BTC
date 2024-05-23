package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.DataPasser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;

public class PurchaseConfirmationController {

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private Label productQuantityLabel;

    @FXML
    public void initialize() {
        DataPasser dataPasser = DataPasser.getInstance();
        productNameLabel.setText(dataPasser.labelTemp);
        productPriceLabel.setText("Price: " + dataPasser.productPrice);
        productQuantityLabel.setText("Quantity: " + dataPasser.productQuantity);
    }

    @FXML
    private void handleConfirmPurchase(ActionEvent event) {
        // Implement your logic for confirming the purchase here
        // For example, deduct the price from the user's balance, update the inventory, etc.

        // Close the popup
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancelPurchase(ActionEvent event) {
        // Close the popup
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
