package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

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

    @FXML
    private void initialize() {
        randomizeButton.setOnAction(event -> changeImages());
    }

    private void changeImages() {
        int random1 = random.nextInt(1, 25);
        Card card1 = CardConstants.createCard(random1);

        URL imageUrl1 = getClass().getResource(card1.getCardPath());
        if (imageUrl1 != null) {
            Image imageNew1 = new Image(imageUrl1.toExternalForm());
            imageView1.setImage(imageNew1);
            label1.setText(card1.getCardName());

        }
        
        int random2 = random.nextInt(1, 25);
        Card card2 = CardConstants.createCard(random2);

        URL imageUrl2 = getClass().getResource(card2.getCardPath());
        if (imageUrl2 != null){
            Image imageNew2 = new Image(imageUrl2.toExternalForm());
            imageView2.setImage(imageNew2);
            label2.setText(card2.getCardName());
        }

        int random3 = random.nextInt(1, 25);
        Card card3 = CardConstants.createCard(random3);

        URL imageUrl3 = getClass().getResource(card3.getCardPath());
        if (imageUrl3 != null){
            Image imageNew3 = new Image(imageUrl3.toExternalForm());
            imageView3.setImage(imageNew3);
            label3.setText(card3.getCardName());
        }

        int random4 = random.nextInt(1, 25);
        Card card4 = CardConstants.createCard(random4);

        URL imageUrl4 = getClass().getResource(card4.getCardPath());
        if (imageUrl4 != null){
            Image imageNew4 = new Image(imageUrl4.toExternalForm());
            imageView4.setImage(imageNew4);
            label4.setText(card4.getCardName());
        }
//        imageView2.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
//        imageView3.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
//        imageView4.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
    }
}
