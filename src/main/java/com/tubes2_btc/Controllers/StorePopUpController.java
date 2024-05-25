package com.tubes2_btc.Controllers;

import java.util.Map;

import com.tubes2_btc.Classes.Card;
import com.tubes2_btc.Classes.DataPasser;
import com.tubes2_btc.Classes.Player;
import com.tubes2_btc.Classes.Store;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StorePopUpController {

    @FXML
    private ImageView imageView;

    @FXML
    private Text labelText1;

    @FXML
    private Button kembali;

    @FXML
    private Button plusButton;

    @FXML
    private Button minusButton;

    @FXML
    private Label quantityLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private Label priceLabel;

    @FXML
    private Label descLabel;

    @FXML
    private Label countActiveDeck;

    private int quantity = 1;
    private int maxQuantity;
    private int productPrice;
    DataPasser dataPasser = DataPasser.getInstance();
    Player player = (dataPasser.currentPlayer == 1) ? dataPasser.player1 : dataPasser.player2;
    private int deckSize = 0;
    private Map<Integer, Card> activeDeck;


    @FXML
    public void initialize() {
        DataPasser dataPasser = DataPasser.getInstance();
        imageView.setImage(dataPasser.imageTemp);
        labelText1.setText(dataPasser.labelTemp);
        productPrice = dataPasser.productPrice;
        player = (dataPasser.currentPlayer == 1) ? dataPasser.player1 : dataPasser.player2;
        activeDeck = player.getActiveDeck();

        for (Card card : activeDeck.values()) {
            if (card.IsEmpty()) {
                deckSize++;
            }
        }
        deckSize--;
        System.out.println(deckSize);
        updatePriceLabel();

        Store store = StorePageController.getStore();
        maxQuantity = store.getProductCount(dataPasser.labelTemp);

        quantityLabel.setText(String.valueOf(quantity));
        updateLabelsAndButtons();
    }

    @FXML
    private void handlePlus(ActionEvent event) {
        if (quantity < maxQuantity) {
            quantity++;
            deckSize--;
            quantityLabel.setText(String.valueOf(quantity));
            updatePriceLabel();
            updateLabelsAndButtons();
        }
    }

    @FXML
    private void handleMinus(ActionEvent event) {
        if (quantity > 1) {
            quantity--;
            deckSize++;
            quantityLabel.setText(String.valueOf(quantity));
            updatePriceLabel();
            updateLabelsAndButtons();
        }
    }

    private void updatePriceLabel() {
        int totalPrice = productPrice * quantity;
        priceLabel.setText("" + totalPrice);
    }

    private void updateLabelsAndButtons() {
        int totalPrice = productPrice * quantity;
        boolean disableConfirmButton = false;

        if (totalPrice > player.getGuldenCount()) {
            descLabel.setText("BTC anda tidak cukup!");
            descLabel.setStyle("-fx-text-fill: red;");
            disableConfirmButton = true;
        } else {
            descLabel.setText("BTC anda setelah beli: " + (player.getGuldenCount() - totalPrice));
            descLabel.setStyle("-fx-text-fill: black;");
        }

        if (deckSize < 0) {
            countActiveDeck.setText("Deck aktif anda penuh!");
            countActiveDeck.setStyle("-fx-text-fill: red;");
            disableConfirmButton = true;
        } else {
            countActiveDeck.setText("Sisa deck aktif anda: " + (deckSize));
            countActiveDeck.setStyle("-fx-text-fill: black;");
        }

        confirmButton.setDisable(disableConfirmButton);
    }


    @FXML
    private void handleConfirm(ActionEvent event) {
        DataPasser dataPasser = DataPasser.getInstance();
        dataPasser.productQuantity = quantity;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/purchase-confirmation.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));

            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setOnShown(e -> {
                stage.setX(parentStage.getX() + (parentStage.getWidth() / 2) - (stage.getWidth() / 2));
                stage.setY(parentStage.getY() + (parentStage.getHeight() / 2) - (stage.getHeight() / 2));
            });

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            Stage storeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            storeStage.close();
        }
    }

    @FXML
    private void handleClickedKembali(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
