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
    private Label changeLabel;

    @FXML
    public void initialize() {
        DataPasser dataPasser = DataPasser.getInstance();
        int change = ((dataPasser.currentPlayer == 1) ? dataPasser.player1 : dataPasser.player2).getGuldenCount() - dataPasser.productPrice * dataPasser.productQuantity;
        productNameLabel.setText("Beli " + dataPasser.labelTemp + "?");
        productPriceLabel.setText("Total Harga: Gd. " + dataPasser.productPrice * dataPasser.productQuantity);
        productQuantityLabel.setText("Kuantitas: " + dataPasser.productQuantity);
        changeLabel.setText("Uang anda setelah pembelian: Gd. " + change);
    }

    @FXML
    private void handleConfirmPurchase(ActionEvent event) {


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
