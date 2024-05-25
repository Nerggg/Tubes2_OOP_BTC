package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.Card;
import com.tubes2_btc.Classes.DataPasser;
import com.tubes2_btc.Classes.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
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
        productPriceLabel.setText("Total Harga: " + dataPasser.productPrice * dataPasser.productQuantity);
        productQuantityLabel.setText("Kuantitas: " + dataPasser.productQuantity);
        changeLabel.setText("Uang anda setelah pembelian: " + change);
    }

    @FXML
    private void handleConfirmPurchase(ActionEvent event) {
        DataPasser dataPasser = DataPasser.getInstance();
        System.out.println(("Sisa di toko sebelum: " + dataPasser.productStoreQuantity));
        dataPasser.productStoreQuantity -= dataPasser.productQuantity;
        Player player = (dataPasser.currentPlayer == 1) ? dataPasser.player1 : dataPasser.player2;
        player.setGuldenCount(player.getGuldenCount() - dataPasser.productPrice * dataPasser.productQuantity);
        System.out.println("\nBarang yang dibeli: " + dataPasser.labelTemp);
        System.out.println("Banyaknya: " + dataPasser.productQuantity);
        System.out.println("Total: " + dataPasser.productPrice * dataPasser.productQuantity);
        System.out.println(("Sisa di toko: " + dataPasser.productStoreQuantity));
        System.out.println(("Sisa uang: " + player.getGuldenCount()));

        StorePageController.getStore().setProductCounts(dataPasser.productTemp.getCardName(), dataPasser.productStoreQuantity);
        for (int i = 0; i < dataPasser.productQuantity; i++){
            player.addToActiveDeck(dataPasser.productTemp);
        }

        dataPasser.mainPageController.updateActiveDeck();
        dataPasser.mainPageController.setGameDataGUI();


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
