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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleClickedPanen(ActionEvent event) {

        List<Integer> key = new ArrayList<>();
        List<Integer> value = new ArrayList<>();

        key.add(Card.CARD_HIU_INDEX);
        value.add(Card.CARD_SIRIP_HIU_INDEX);
        key.add(Card.CARD_SAPI_INDEX);
        value.add(Card.CARD_SUSU_INDEX);
        key.add(Card.CARD_DOMBA_INDEX);
        value.add(Card.CARD_DAGING_DOMBA_INDEX);
        key.add(Card.CARD_KUDA_INDEX);
        value.add(Card.CARD_DAGING_KUDA_INDEX);
        key.add(Card.CARD_AYAM_INDEX);
        value.add(Card.CARD_TELUR_INDEX);
        key.add(Card.CARD_BERUANG_INDEX);
        value.add(Card.CARD_DAGING_BERUANG_INDEX);
        key.add(Card.CARD_BIJI_JAGUNG_INDEX);
        value.add(Card.CARD_JAGUNG_INDEX);
        key.add(Card.CARD_BIJI_LABU_INDEX);
        value.add(Card.CARD_LABU_INDEX);
        key.add(Card.CARD_BIJI_STROBERI_INDEX);
        value.add(Card.CARD_STROBERI_INDEX);


        DataPasser dataPasser = DataPasser.getInstance();

        Card produk = dataPasser.infoCard;

        Player player = (dataPasser.currentPlayer == 1) ? dataPasser.player1 : dataPasser.player2;


        int indexHasil = dataPasser.indexCard;

        int target = 0;
        for (int i = 0; i < key.size(); i++) {
            if (Card.createCard(key.get(i)).getCardName().equals(produk.getCardName())){
                System.out.println("found");
                target = value.get(i);
                break;
            }
        }

        System.out.println(Card.createCard(target).getCardName());
        player.addToActiveDeck(Card.createCard(target));
        player.setFarmAt(indexHasil, Card.createCard(Card.CARD_EMPTY_INDEX));
        System.out.println("Tombol 'Panen' ditekan.");
        dataPasser.mainPageController.updateActiveDeck();
        dataPasser.mainPageController.updateFarm();
        dataPasser.mainPageController.setGameDataGUI();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public boolean checkCanHarvest() {
        DataPasser dataPasser = DataPasser.getInstance();
        System.out.println("Current player: " + dataPasser.currentPlayer);
        System.out.println("Current farm view: " + dataPasser.currentFarmView);
        return dataPasser.currentFarmView == dataPasser.currentPlayer;
    }

    public boolean checkPlayerActiveDeckFull() {
        DataPasser dataPasser = DataPasser.getInstance();
        Player player = (dataPasser.currentPlayer == 1) ? dataPasser.player1 : dataPasser.player2;
        
        return player.getActiveDeckFreeSlots() == 0;
    }

    @FXML
    public void initialize() {
        DataPasser dataPasser = DataPasser.getInstance();
        if (dataPasser.infoCard.getClass().getSimpleName().equals("Animal")) {
            Animal card = (Animal) dataPasser.infoCard;
            // Nama item
            labelText1.setText(card.getCardName());
            // Berat atau umur
            labelText2.setText("Berat:");
            // Berat atau umur sekarang
            labelValue1.setText(Integer.toString(card.getWeight()));
            // Berat atau umur untuk dipanen
            labelValue2.setText("(" + Integer.toString(card.getHarvestWeight()) + ")");
            // Item aktif
            labelItemAktifValue.setText(card.getCardActive());
            // set image card
            Image image = new Image(getClass().getResource(card.getCardPath()).toExternalForm());
            imageView.setImage(image);
            // tombol panen
            panen.setDisable(card.getWeight() < card.getHarvestWeight() || !checkCanHarvest() || checkPlayerActiveDeckFull());
        }
        if (dataPasser.infoCard.getClass().getSimpleName().equals("Plant")) {
            Plant card = (Plant) dataPasser.infoCard;
            // Nama item
            labelText1.setText(card.getCardName());
            // Berat atau umur
            labelText2.setText("Berat:");
            // Berat atau umur sekarang
            labelValue1.setText(Integer.toString(card.getAge()));
            // Berat atau umur untuk dipanen
            labelValue2.setText("(" + Integer.toString(card.getHarvestAge()) + ")");
            // Item aktif
            labelItemAktifValue.setText(card.getCardActive());
            // set image card
            Image image = new Image(getClass().getResource(card.getCardPath()).toExternalForm());
            imageView.setImage(image);
            // tombol panen
            panen.setDisable(card.getAge() < card.getHarvestAge() || !checkCanHarvest() || checkPlayerActiveDeckFull());
        }
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
            // tombol panen
            panen.setDisable(card.getHarvestAge() < card.getHarvestAge() || !checkCanHarvest() || checkPlayerActiveDeckFull());
        }
    }
}
