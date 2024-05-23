package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.DataPasser;
import com.tubes2_btc.Classes.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    private int quantity = 1;
    private int maxQuantity;

    @FXML
    public void initialize() {
        DataPasser dataPasser = DataPasser.getInstance();
        imageView.setImage(dataPasser.imageTemp);
        labelText1.setText(dataPasser.labelTemp);

        Store store = StorePageController.getStore();
        maxQuantity = store.getProductCount(dataPasser.labelTemp);

        quantityLabel.setText(String.valueOf(quantity));
    }

    @FXML
    private void handlePlus(ActionEvent event) {
        if (quantity < maxQuantity) {
            quantity++;
            quantityLabel.setText(String.valueOf(quantity));
        }
    }

    @FXML
    private void handleMinus(ActionEvent event) {
        if (quantity > 1) {
            quantity--;
            quantityLabel.setText(String.valueOf(quantity));
        }
    }

    @FXML
    private void handleConfirm(ActionEvent event) {
        // Implement your logic for confirming the quantity selection
        // For example, you could update the store inventory here

        // Close the popup
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleClickedKembali(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
