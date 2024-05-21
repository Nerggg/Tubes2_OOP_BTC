package com.tubes2_btc.Controllers;
import com.tubes2_btc.Classes.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.net.URL;

public class CardInfoController {

    @FXML
    private Button kembali;

    @FXML
    private Button panen;

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
        // Logika untuk menangani tombol "Kembali" ditekan
        System.out.println("Tombol 'Kembali' ditekan.");
        // Misalnya, kembali ke layar sebelumnya atau menutup jendela saat ini
    }

    @FXML
    private void handleClickedPanen(ActionEvent event) {
        // Logika untuk menangani tombol "Panen" ditekan
        System.out.println("Tombol 'Panen' ditekan.");
        // Misalnya, menjalankan logika panen atau memperbarui data
    }

    @FXML
    public void initialize(Animal animal) {
        // Nama item
        labelText1.setText(animal.getCardName());
        // Berat atau umur
        labelText2.setText("Berat:");
        // Berat atau umur sekarang
        labelValue1.setText(Integer.toString(animal.getWeight()));
        // Berat atau umur untuk dipanen
        labelValue2.setText(Integer.toString(animal.getHarvestWeight()));
        // Item aktif
        labelItemAktifValue.setText(animal.getCardActive());
        // set image card
        Image image = new Image(getClass().getResource(animal.getCardPath()).toExternalForm());
        imageView.setImage(image);
        // tombol panen
        panen.setDisable(animal.getWeight()<animal.getHarvestWeight());
    }

    public void initialize(Plant plant) {
        // Nama item
        labelText1.setText(plant.getCardName());
        // Berat atau umur
        labelText2.setText("Berat:");
        // Berat atau umur sekarang
        labelValue1.setText(Integer.toString(plant.getAge()));
        // Berat atau umur untuk dipanen
        labelValue2.setText(Integer.toString(plant.getHarvestAge()));
        // Item aktif
        labelItemAktifValue.setText(plant.getCardActive());
        // set image card
        Image image = new Image(getClass().getResource(plant.getCardPath()).toExternalForm());
        imageView.setImage(image);
        // tombol panen
        panen.setDisable(plant.getAge()<plant.getHarvestAge());
    }
}
