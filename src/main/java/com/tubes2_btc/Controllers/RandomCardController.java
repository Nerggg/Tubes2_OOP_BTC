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
    private boolean card1_Available = false;
    private boolean card2_Available = false;
    private boolean card3_Available = false;
    private boolean card4_Available = false;
    private List<Integer> cardIndexList = new ArrayList<>();

    private int chosenCards;
    private int maxChosenCards;
    private int deckCount;
    private List<Card> deck;

    private final Random random = new Random();

    // Main page controller
    private MainPageController mainPageController;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

    @FXML
    private void initialize() {
        // Get data passer
        DataPasser dataPasser = DataPasser.getInstance();

        // Set data
        chosenCards = 0;
        maxChosenCards = (dataPasser.currentPlayer == 1) ? dataPasser.player1.getActiveDeckFreeSlots() : dataPasser.player2.getActiveDeckFreeSlots();
        deckCount = (dataPasser.currentPlayer == 1) ? dataPasser.player1.getDeckCount() : dataPasser.player2.getDeckCount();
        deck = (dataPasser.currentPlayer == 1) ? dataPasser.player1.getDeck() : dataPasser.player2.getDeck();

        // Set change cards
        changeImages();
        randomizeButton.setOnAction(event -> changeImages());
    }

    private void changeImages() {
        // Reset card chosen count
        chosenCards = 0;
        cardIndexList.clear();

        // ======== Get random cards ========
        // Card 1
        stackPane1.setBackground(new Background(new BackgroundFill(Color.web("#D9D9D9"), CornerRadii.EMPTY, null)));
        stackPane2.setBackground(new Background(new BackgroundFill(Color.web("#D9D9D9"), CornerRadii.EMPTY, null)));
        stackPane3.setBackground(new Background(new BackgroundFill(Color.web("#D9D9D9"), CornerRadii.EMPTY, null)));
        stackPane4.setBackground(new Background(new BackgroundFill(Color.web("#D9D9D9"), CornerRadii.EMPTY, null)));

        if (deckCount >= 1) {
            card1_Available = true;

            int random1 = random.nextInt(deckCount);
            card1 = deck.get(random1);
            cardIndexList.add(random1);
    
            URL imageUrl1 = getClass().getResource(card1.getCardPath());
            if (imageUrl1 != null) {
                Image imageNew1 = new Image(imageUrl1.toExternalForm());
                imageView1.setImage(imageNew1);
                label1.setText(card1.getCardName());
            }
        }

        // Card 2
        if (deckCount >= 2) {
            card2_Available = true;

            int random2;
            do {
                random2 = random.nextInt(deckCount);
            } while (cardIndexList.contains(random2));
            card2 = deck.get(random2);
            cardIndexList.add(random2);
    
            URL imageUrl2 = getClass().getResource(card2.getCardPath());
            if (imageUrl2 != null){
                Image imageNew2 = new Image(imageUrl2.toExternalForm());
                imageView2.setImage(imageNew2);
                label2.setText(card2.getCardName());
            }
        }

        // Card 3
        if (deckCount >= 3) {
            card3_Available = true;

            int random3;
            do {
                random3 = random.nextInt(deckCount);
            } while (cardIndexList.contains(random3));
            card3 = deck.get(random3);
            cardIndexList.add(random3);
    
            URL imageUrl3 = getClass().getResource(card3.getCardPath());
            if (imageUrl3 != null){
                Image imageNew3 = new Image(imageUrl3.toExternalForm());
                imageView3.setImage(imageNew3);
                label3.setText(card3.getCardName());
            }
        }

        // Card 4
        if (deckCount >= 4) {
            card4_Available = true;

            int random4;
            do {
                random4 = random.nextInt(deckCount);
            } while (cardIndexList.contains(random4));
            card4 = deck.get(random4);
            cardIndexList.add(random4);

            URL imageUrl4 = getClass().getResource(card4.getCardPath());
            if (imageUrl4 != null){
                Image imageNew4 = new Image(imageUrl4.toExternalForm());
                imageView4.setImage(imageNew4);
                label4.setText(card4.getCardName());
            }
        }

        // imageView2.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
        // imageView3.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
        // imageView4.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
    }

    @FXML
    private List<Card> handleClicked(ActionEvent event) {
        Background currentBackground1 = stackPane1.getBackground();
        Background currentBackground2 = stackPane2.getBackground();
        Background currentBackground3 = stackPane3.getBackground();
        Background currentBackground4 = stackPane4.getBackground();

        if (currentBackground1 != null && currentBackground1.getFills().get(0).getFill().equals(Color.web("#4BB543")) && card1_Available) {
            cardList.add(card1);
        }
        if (currentBackground2 != null && currentBackground2.getFills().get(0).getFill().equals(Color.web("#4BB543")) && card2_Available) {
            cardList.add(card2);
        }
        if (currentBackground3 != null && currentBackground3.getFills().get(0).getFill().equals(Color.web("#4BB543")) && card3_Available) {
            cardList.add(card3);
        }
        if (currentBackground4 != null && currentBackground4.getFills().get(0).getFill().equals(Color.web("#4BB543")) && card4_Available) {
            cardList.add(card4);
        }
        for (Card card : cardList) {
            System.out.print(card.getCardName()+",");
        }

        // Add cards to deck
        DataPasser dataPasser = DataPasser.getInstance();
        Player player = (dataPasser.currentPlayer == 1) ? dataPasser.player1 : dataPasser.player2;

        for (Card card : cardList) {
            player.addToActiveDeck(card);
            player.getDeck().remove(card);
        }
        dataPasser.mainPageController.updateActiveDeck();
        dataPasser.mainPageController.setGameDataGUI();

        // Run bear attack
        dataPasser.mainPageController.testPrintSlots();
        dataPasser.mainPageController.bearAttackHandler();
        
        // Close stage
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

            chosenCards--;
        } else if (currentBackground != null && currentBackground.getFills().get(0).getFill().equals(Color.web("#D9D9D9")) && chosenCards < maxChosenCards) {
            // Change to green color (#4BB543)
            clickedPane.setBackground(new Background(new BackgroundFill(Color.web("#4BB543"), CornerRadii.EMPTY, null)));

            chosenCards++;
        }
        System.out.println(chosenCards + " / " + maxChosenCards);

        for (Card card : cardList) {
            System.out.print(card.getCardName());
        }
        System.out.println();
    }
}
