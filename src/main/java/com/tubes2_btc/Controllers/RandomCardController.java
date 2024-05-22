package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;

public class RandomCardController {
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Button randomizeButton;

    private final Random random = new Random();

    // Main page controller
    private MainPageController mainPageController;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @FXML
    private void initialize() {
        // changeImages();
        randomizeButton.setOnAction(event -> changeImages());
    }

    private void changeImages() {
        // Get DataPasser
        DataPasser dataPasser = DataPasser.getInstance();

        // ======== Get random cards ========
        // Card 1
        int random1 = random.nextInt(1, 25);
        Card card1 = CardConstants.createCard(random1);

        URL imageUrl1 = getClass().getResource(card1.getCardPath());
        if (imageUrl1 != null) {
            Image imageNew1 = new Image(imageUrl1.toExternalForm());
            imageView1.setImage(imageNew1);
            label1.setText(card1.getCardName());
        }
        mainPageController.setFarmAt(0, card1, dataPasser.player1);

        // Card 2
        int random2 = random.nextInt(1, 25);
        Card card2 = CardConstants.createCard(random2);

        URL imageUrl2 = getClass().getResource(card2.getCardPath());
        if (imageUrl2 != null){
            Image imageNew2 = new Image(imageUrl2.toExternalForm());
            imageView2.setImage(imageNew2);
            label2.setText(card2.getCardName());
        }
        mainPageController.setFarmAt(1, card2, dataPasser.player1);

        // Card 3
        int random3 = random.nextInt(1, 25);
        Card card3 = CardConstants.createCard(random3);

        URL imageUrl3 = getClass().getResource(card3.getCardPath());
        if (imageUrl3 != null){
            Image imageNew3 = new Image(imageUrl3.toExternalForm());
            imageView3.setImage(imageNew3);
            label3.setText(card3.getCardName());
        }
        mainPageController.setFarmAt(2, card3, dataPasser.player1);

        // Card 4
        int random4 = random.nextInt(1, 25);
        Card card4 = CardConstants.createCard(random4);

        URL imageUrl4 = getClass().getResource(card4.getCardPath());
        if (imageUrl4 != null){
            Image imageNew4 = new Image(imageUrl4.toExternalForm());
            imageView4.setImage(imageNew4);
            label4.setText(card4.getCardName());
        }
        mainPageController.setFarmAt(3, card4, dataPasser.player1);
//        imageView2.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
//        imageView3.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
//        imageView4.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
    }

    @FXML
    private void handleClicked(ActionEvent event) {
        if (mainPageController != null) {
            mainPageController.testFunction();;
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
