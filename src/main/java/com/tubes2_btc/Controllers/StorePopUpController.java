package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.DataPasser;
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
        DataPasser dataPasser = DataPasser.getInstance();
        dataPasser.productPrice = // Retrieve the product price from the store or dataPasser;
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
        }
    }

    @FXML
    private void handleClickedKembali(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
