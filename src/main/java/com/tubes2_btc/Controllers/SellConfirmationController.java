package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.DataPasser;
import com.tubes2_btc.Classes.Player;
import com.tubes2_btc.Classes.Product;
import com.tubes2_btc.Classes.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SellConfirmationController {

    @FXML
    private Label productNameLabel;

    @FXML
    private Label productPriceLabel;

    @FXML
    private Label changeLabel;

    private Product product;

    public void setProduct(Product product) {
        this.product = product;
        updateProductInfo();
    }

    private void updateProductInfo() {
        DataPasser dataPasser = DataPasser.getInstance();
        Player player = (dataPasser.currentPlayer == 1) ? dataPasser.player1 : dataPasser.player2;
        productNameLabel.setText(product.getCardName());
        productPriceLabel.setText("Price: " + product.getSellPrice());
        changeLabel.setText("Uang anda setelah jual: " + (player.getGuldenCount() + product.getSellPrice()));
    }

    @FXML
    private void handleConfirmSell(ActionEvent event) {
        DataPasser dataPasser = DataPasser.getInstance();
        Player player = (dataPasser.currentPlayer == 1) ? dataPasser.player1 : dataPasser.player2;
        player.setGuldenCount(player.getGuldenCount() + product.getSellPrice());
        Store store = StorePageController.getStore();
        player.removeFromActiveDeck(product);
        store.addProduct(product, 1);


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        dataPasser.mainPageController.updateActiveDeck();
        dataPasser.mainPageController.setGameDataGUI();

    }

    @FXML
    private void handleCancelSell(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
