package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    @FXML
    private StackPane stackPane1;
    @FXML
    private StackPane stackPane2;
    @FXML
    private StackPane stackPane3;
    @FXML
    private StackPane stackPane4;

    private List<Card> cardList = new ArrayList<>();
    private Iterator<Card> iterator = cardList.iterator();
    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;

    private final Random random = new Random();

    // Main page controller
    private MainPageController mainPageController;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @FXML
    private void initialize() {
        changeImages();
        randomizeButton.setOnAction(event -> changeImages());
    }

    private void changeImages() {
        // Get DataPasser
        DataPasser dataPasser = DataPasser.getInstance();

        // ======== Get random cards ========
        // Card 1
        stackPane1.setBackground(new Background(new BackgroundFill(Color.web("#D9D9D9"), CornerRadii.EMPTY, null)));
        stackPane2.setBackground(new Background(new BackgroundFill(Color.web("#D9D9D9"), CornerRadii.EMPTY, null)));
        stackPane3.setBackground(new Background(new BackgroundFill(Color.web("#D9D9D9"), CornerRadii.EMPTY, null)));
        stackPane4.setBackground(new Background(new BackgroundFill(Color.web("#D9D9D9"), CornerRadii.EMPTY, null)));

        int random1 = random.nextInt(1, 25);
        card1 = CardConstants.createCard(random1);

        URL imageUrl1 = getClass().getResource(card1.getCardPath());
        if (imageUrl1 != null) {
            Image imageNew1 = new Image(imageUrl1.toExternalForm());
            imageView1.setImage(imageNew1);
            label1.setText(card1.getCardName());
        }
        // mainPageController.setFarmAt(0, card1, dataPasser.player1);

        // Card 2
        int random2 = random.nextInt(1, 25);
        card2 = CardConstants.createCard(random2);

        URL imageUrl2 = getClass().getResource(card2.getCardPath());
        if (imageUrl2 != null){
            Image imageNew2 = new Image(imageUrl2.toExternalForm());
            imageView2.setImage(imageNew2);
            label2.setText(card2.getCardName());
        }
        // mainPageController.setFarmAt(1, card2, dataPasser.player1);

        // Card 3
        int random3 = random.nextInt(1, 25);
        card3 = CardConstants.createCard(random3);

        URL imageUrl3 = getClass().getResource(card3.getCardPath());
        if (imageUrl3 != null){
            Image imageNew3 = new Image(imageUrl3.toExternalForm());
            imageView3.setImage(imageNew3);
            label3.setText(card3.getCardName());
        }
        // mainPageController.setFarmAt(2, card3, dataPasser.player1);

        // Card 4
        int random4 = random.nextInt(1, 25);
        card4 = CardConstants.createCard(random4);

        URL imageUrl4 = getClass().getResource(card4.getCardPath());
        if (imageUrl4 != null){
            Image imageNew4 = new Image(imageUrl4.toExternalForm());
            imageView4.setImage(imageNew4);
            label4.setText(card4.getCardName());
        }
        // mainPageController.setFarmAt(3, card4, dataPasser.player1);
//        imageView2.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
//        imageView3.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
//        imageView4.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
    }

    @FXML
    private List<Card> handleClicked(ActionEvent event) {
        if (mainPageController != null) {
            mainPageController.testFunction();;
        }
        Background currentBackground1 = stackPane1.getBackground();
        Background currentBackground2 = stackPane2.getBackground();
        Background currentBackground3 = stackPane3.getBackground();
        Background currentBackground4 = stackPane4.getBackground();

        if (currentBackground1 != null && currentBackground1.getFills().get(0).getFill().equals(Color.web("#4BB543"))) {
            cardList.add(card1);
        }
        if (currentBackground2 != null && currentBackground2.getFills().get(0).getFill().equals(Color.web("#4BB543"))) {
            cardList.add(card2);
        }
        if (currentBackground3 != null && currentBackground3.getFills().get(0).getFill().equals(Color.web("#4BB543"))) {
            cardList.add(card3);
        }
        if (currentBackground4 != null && currentBackground4.getFills().get(0).getFill().equals(Color.web("#4BB543"))) {
            cardList.add(card4);
        }
        for (Card card : cardList) {
            System.out.print(card.getCardName()+",");
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        return cardList;
    }

    @FXML
    private void handleStackPaneClick(MouseEvent event) {
        StackPane clickedPane = (StackPane) event.getSource();
        Background currentBackground = clickedPane.getBackground();
        Label label = (Label) clickedPane.lookup(".label");
        // Check if the current background is green (#4BB543)
        if (currentBackground != null && currentBackground.getFills().get(0).getFill().equals(Color.web("#4BB543"))) {
            // Change to light gray color (#D9D9D9)
            clickedPane.setBackground(new Background(new BackgroundFill(Color.web("#D9D9D9"), CornerRadii.EMPTY, null)));
        } else {
            // Change to green color (#4BB543)
            clickedPane.setBackground(new Background(new BackgroundFill(Color.web("#4BB543"), CornerRadii.EMPTY, null)));
        }
        for (Card card : cardList) {
            System.out.print(card.getCardName());
        }
        System.out.println();
    }
}
