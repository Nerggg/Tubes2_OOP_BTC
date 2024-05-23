package com.tubes2_btc.Controllers;
import com.tubes2_btc.Classes.*;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.net.URL;

public class DeckCardInfoController {

    @FXML
    private Button kembali;

    @FXML
    private Button jual;

    @FXML
    private ImageView imageView;

    @FXML
    private Text labelText1;

    @FXML
    private Text labelText2;

    @FXML
    private Text labelValue1;

    @FXML
    private Text labelValue2;

    @FXML
    private Text labelItemAktif;

    @FXML
    private Text labelItemAktifValue;

    @FXML
    private void handleClickedKembali(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleClickedJual(ActionEvent event) {
        // Logika untuk menangani tombol "Panen" ditekan
        System.out.println("Tombol 'Jual' ditekan.");
        // Misalnya, menjalankan logika panen atau memperbarui data
    }

    @FXML
    public void initialize() {
        DataPasser dataPasser = DataPasser.getInstance();
        if (dataPasser.infoCard.getClass().getSimpleName().equals("Product")) {
            Product card = (Product) dataPasser.infoCard;
            // Nama item
            labelText1.setText(card.getCardName());
            // Berat atau umur
            labelText2.setText("Berat:");
            // Berat atau umur sekarang
            labelValue1.setText(Integer.toString(card.getHarvestAge()));
            // Berat atau umur untuk dipanen
            labelValue2.setText("(" + Integer.toString(card.getHarvestAge()) + ")");
            // Item aktif
            labelItemAktifValue.setText(card.getCardActive());
            // set image card
            Image image = new Image(getClass().getResource(card.getCardPath()).toExternalForm());
            imageView.setImage(image);
        }
    }
}
