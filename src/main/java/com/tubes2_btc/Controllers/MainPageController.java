package com.tubes2_btc.Controllers;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tubes2_btc.Classes.Animal;
import com.tubes2_btc.Classes.Card;
import com.tubes2_btc.Classes.CardConstants;
import com.tubes2_btc.Classes.DataPasser;
import com.tubes2_btc.Classes.Plant;
import com.tubes2_btc.Classes.Player;
import com.tubes2_btc.Classes.Product;
import com.tubes2_btc.Classes.Store;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javafx.scene.media.Media;
import javafx.util.Duration;


public class MainPageController {
    // Misc. variables
    private Card draggedCard;
    private int draggedCardIndex;
    private boolean draggedIsFarm;

    // Setup variables
    private Player player1 = new Player();
    private Player player2 = new Player();
    private Store store = new Store(new ArrayList<>());

    // Set player 1
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    // Set player 2
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    // Game state variables
    private int currentPlayer = 1;
    private int currentFarmView = 1;
    private int currentTurn = 0;

    // Set game state procedure
    public void loadGameState(Player player1, Player player2, int currentTurn, Store store) {
        // Set players
        this.player1.copyFrom(player1);
        this.player2.copyFrom(player2);

        // Set store
        this.store = store;

        // Set current turn
        this.currentTurn = currentTurn;

        // Set current player
        this.currentPlayer = (currentTurn % 2 == 1) ? 1 : 2;

        // Set current farm view
        this.currentFarmView = this.currentPlayer;

        // Update farm
        updateFarm();
        updateActiveDeck();
        setGameDataGUI();
    }

    public Player getPlayer1(){
        return this.player1;
    }

    public Player getPlayer2(){
        return this.player2;
    }

    public int getCurrentTurn(){
        return this.currentTurn;
    }

    public Store getStore(){
        return this.store;
    }
    
    // GUI variables
    private List<Node> farmSlots_1 = new ArrayList<>();
    private List<Node> farmSlots_2 = new ArrayList<>();

    private List<Node> activeDeckSlots_1 = new ArrayList<>();
    private List<Node> activeDeckSlots_2 = new ArrayList<>();

    // Misc. methods
    public void swapNodes(Node node1, Node node2) {
        ImageView imageView1 = null;
        Label label1 = null;

        for (javafx.scene.Node childPane : ((Pane) node1).getChildren()) {
            if (childPane instanceof ImageView) {
                imageView1 = (ImageView) childPane;
            } else if (childPane instanceof Label) {
                label1 = (Label) childPane;
            }
        }

        ImageView imageView2 = null;
        Label label2 = null;

        for (javafx.scene.Node childPane : ((Pane) node2).getChildren()) {
            if (childPane instanceof ImageView) {
                imageView2 = (ImageView) childPane;
            } else if (childPane instanceof Label) {
                label2 = (Label) childPane;
            }
        }

        // Swap images and names
        Image tempImage = imageView1.getImage();
        imageView1.setImage(imageView2.getImage());
        imageView2.setImage(tempImage);

        String tempString = label1.getText();
        label1.setText(label2.getText());
        label2.setText(tempString);
    }
    
    public void handleDragAndDrop(Card dragged, Card dropped, int draggedIndex, int droppedIndex, boolean draggedIsFarm, boolean droppedIsFarm) {
        playMcLarenLuWarnaApaBos();
        System.out.println("Dragged: " + dragged.getCardName() + " (" + draggedIndex + ")");
        System.out.println("Dropped: " + dropped.getCardName() + " (" + droppedIndex + ")");
        if (!draggedIsFarm && !droppedIsFarm) {
            // Swap at active deck
            Player p = (currentPlayer == 1) ? player1 : player2;

            p.swapSlots(draggedIndex, droppedIndex, p.getActiveDeck(), p.getActiveDeck());

            // Swap images and names
            List<Node> activeDeckSlots = (currentPlayer == 1) ? activeDeckSlots_1 : activeDeckSlots_2;

            Node slot_dragged = activeDeckSlots.get(draggedIndex);
            Node slot_dropped = activeDeckSlots.get(droppedIndex);

            swapNodes(slot_dragged, slot_dropped);
        } else if (draggedIsFarm && !droppedIsFarm) {
            if (currentPlayer == currentFarmView) {
                // Swap at farm
                Player p = (currentPlayer == 1) ? player1 : player2;
                
                p.swapSlots(draggedIndex, droppedIndex, p.getFarm(), p.getActiveDeck());
                
                // Swap images and names
                List<Node> farmSlots = (currentPlayer == 1) ? farmSlots_1 : farmSlots_2;
                List<Node> activeDeckSlots = (currentPlayer == 1) ? activeDeckSlots_1 : activeDeckSlots_2;

                Node slot_dragged = farmSlots.get(draggedIndex);
                Node slot_dropped = activeDeckSlots.get(droppedIndex);

                swapNodes(slot_dragged, slot_dropped);
            
            }
        } else if (draggedIsFarm && droppedIsFarm) {
            if (currentPlayer == currentFarmView) {
                // Swap at farm
                Player p = (currentPlayer == 1) ? player1 : player2;
                manipulateFarm(draggedIndex, droppedIndex, draggedIsFarm, "move", p);
            }
        } else if (!draggedIsFarm && droppedIsFarm) {
            Player player = (currentPlayer == 1) ? player1 : player2;
            Player opponent = (currentPlayer == 1) ? player2 : player1;
            
            if (currentPlayer != currentFarmView) {
                switch (draggedCard.getCardName()) {
                    case CardConstants.CARD_DESTROY:
                        if (dropped.getCardName() != CardConstants.CARD_EMPTY && !dropped.isProtected()) {
                            deleteCard(droppedIndex, true, opponent);
                            deleteCard(draggedIndex, false, player);
                        }
                        break;
                    case CardConstants.CARD_DELAY:
                        if (dropped.getCardName() != CardConstants.CARD_EMPTY) {
                            dropped.delay();
                            deleteCard(draggedIndex, false, player);
                        }
                        break;
                }
            } else {
                switch (draggedCard.getCardName()) {
                    case CardConstants.CARD_ACCELERATE:
                        if (dropped.getCardName() != CardConstants.CARD_EMPTY) {
                            dropped.accelerate();
                            deleteCard(draggedIndex, false, player);
                        }
                        break;
                    case CardConstants.CARD_INSTANT_HARVEST:
                        if (dropped instanceof Plant || dropped instanceof Animal) {
                            deleteCard(draggedIndex, false, player);
                            deleteCard(droppedIndex, true, player);
                            player.addToActiveDeck(dropped.harvest());
                            updateActiveDeck();
                        }
                        break;
                    case CardConstants.CARD_PROTECT:
                        if (dropped.getCardName() != CardConstants.CARD_EMPTY) {
                            deleteCard(draggedIndex, false, player);
                            dropped.protect();
                        }
                        break;
                    case CardConstants.CARD_TRAP:
                        if (dropped.getCardName() != CardConstants.CARD_EMPTY) {
                            deleteCard(draggedIndex, false, player);
                            dropped.trap();
                        }
                        break;
                    default:
                        if (dragged instanceof Product) {
                            if (dropped instanceof Animal) {
                                if (((Animal) dropped).feed((Product) dragged)) {
                                    deleteCard(draggedIndex, false, player);
                                }
                            }
                        } else if (dragged instanceof Animal || dragged instanceof Plant) {
                            // Swap from deck to farm
                            player.swapSlots(draggedIndex, droppedIndex, player.getActiveDeck(), player.getFarm());

                            // Swap images and names
                            List<Node> farmSlots = (currentPlayer == 1) ? farmSlots_1 : farmSlots_2;
                            List<Node> activeDeckSlots = (currentPlayer == 1) ? activeDeckSlots_1 : activeDeckSlots_2;

                            Node slot_dragged = activeDeckSlots.get(draggedIndex);
                            Node slot_dropped = farmSlots.get(droppedIndex);

                            swapNodes(slot_dragged, slot_dropped);
                        }
                        break;
                }
            }
        }
    }

    public void initializeSlot(Node child, int i, boolean isFarm, List<Node> farmSlots, List<Node> activeDeckSlots, Player player) {
        DataPasser dataPasser = DataPasser.getInstance();

        Pane pane = (Pane) child;
        ImageView imageView = null;
        Label label = null;

        // Initialize variables
        for (javafx.scene.Node childPane : pane.getChildren()) {
            if (childPane instanceof ImageView) {
                imageView = (ImageView) childPane;
            } else if (childPane instanceof Label) {
                label = (Label) childPane;
            }
        }

        // Push to farm slots
        if (isFarm)
            farmSlots.add(child);
        else
            activeDeckSlots.add(child);

        // Set farm slot event handlers
        child.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Set currently dragged card
                int underscoreIndex = child.getId().indexOf("_");
                draggedCardIndex = Integer.parseInt(child.getId().substring(underscoreIndex + 1));

                String slot_type = child.getId().substring(0, underscoreIndex);

                if (slot_type.equals("Ladang"))    draggedIsFarm = true;
                else                                        draggedIsFarm = false;

                Player p;
                if (!draggedIsFarm) {
                    p = (currentPlayer == 1) ? player1 : player2;
                } else {
                    p = (currentFarmView == 1) ? player1 : player2;

                }
                draggedCard = (draggedIsFarm) ? p.getFarm().get(draggedCardIndex) : p.getActiveDeck().get(draggedCardIndex);
                
                // Event handler
                Dragboard db = child.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putString(child.getId());
                db.setContent(content);

                // System.out.println("Drag detected for: " + child.getId());
                mouseEvent.consume();

                // deleteCard(draggedCard, isFarm, player1);
                // System.out.println("Drag detected for: " + child.getId());
            }
        });

        child.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getGestureSource() != child && dragEvent.getDragboard().hasString()) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                }
                // System.out.println("Drag over for: " + child.getId());
                dragEvent.consume();
            }
        });

        child.setOnDragDropped(new EventHandler<DragEvent>() {
            
            @Override
            public void handle(DragEvent dragEvent) {
                // Get dropped card number
                int underscoreIndex = child.getId().indexOf("_");
                int droppedCardIndex = Integer.parseInt(child.getId().substring(underscoreIndex + 1));

                String slot_type = child.getId().substring(0, underscoreIndex);

                boolean droppedIsFarm;
                if (slot_type.equals("Ladang"))    droppedIsFarm = true;
                else                                        droppedIsFarm = false;

                Player p;
                if (!droppedIsFarm) {
                    p = (currentPlayer == 1) ? player1 : player2;
                } else {
                    p = (currentFarmView == 1) ? player1 : player2;
                }

                Card droppedCard = (droppedIsFarm) ? p.getFarm().get(droppedCardIndex) : p.getActiveDeck().get(droppedCardIndex);

                // Handle drag and drop event
                handleDragAndDrop(draggedCard, droppedCard, draggedCardIndex, droppedCardIndex, draggedIsFarm, droppedIsFarm);

                // // Swap farm slots
                // if (currentPlayer == currentFarmView) {
                //     Map<Integer, Card> cont1, cont2;
                //     Player p = (currentFarmView == 1) ? player1 : player2;

                //     cont1 = (draggedIsFarm) ? p.getFarm() : p.getActiveDeck();
                //     cont2 = (droppedIsFarm) ? p.getFarm() : p.getActiveDeck();

                //     p.swapSlots(draggedCardIndex, droppedCardIndex, cont1, cont2);

                //     // Swap images and names
                //     Node slot_dragged;
                //     if (draggedIsFarm)  slot_dragged = farmSlots.get(draggedCardIndex);
                //     else                slot_dragged = activeDeckSlots.get(draggedCardIndex);

                //     swapNodes(child, slot_dragged);
                // }

                // Event handler
                Dragboard db = dragEvent.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    // System.out.println("Drag dropped for: " + child.getId());
                    success = true;
                }
                dragEvent.setDropCompleted(success);
                dragEvent.consume();
            }
        });

        if (isFarm) {
            child.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Player p = (currentFarmView == 1) ? player1 : player2;

                    // if (p.getFarm().get(i).getClass().getSimpleName().equals("Animal") || p.getFarm().get(i).getClass().getSimpleName().equals("Plant") || p.getFarm().get(i).getClass().getSimpleName().equals("Product")) {;
                    if (p.getFarm().get(i).getClass().getSimpleName().equals("Animal") || p.getFarm().get(i).getClass().getSimpleName().equals("Plant")) {;
                        dataPasser.infoCard = p.getFarm().get(i);
                        dataPasser.indexCard = i;
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/card-info.fxml"));
                            Parent root = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setScene(new Scene(root));

                            // Get the parent stage (assuming the button is within a stage)
                            Stage parentStage = (Stage) ((Parent) mouseEvent.getSource()).getScene().getWindow();

                            // Center the new stage in the parent stage
                            stage.setOnShown(e -> {
                                stage.setX(parentStage.getX() + (parentStage.getWidth() / 2) - (stage.getWidth() / 2));
                                stage.setY(parentStage.getY() + (parentStage.getHeight() / 2) - (stage.getHeight() / 2));
                            });
                            dataPasser.stage = stage;
                            stage.showAndWait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        else {
            child.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Player p = (currentPlayer == 1) ? player1 : player2;

                    if (p.getActiveDeck().get(i).getClass().getSimpleName().equals("Product")) {;
                        dataPasser.infoCard = p.getActiveDeck().get(i);
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/deck-card-info.fxml"));
                            Parent root = fxmlLoader.load();
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.setScene(new Scene(root));

                            // Get the parent stage (assuming the button is within a stage)
                            Stage parentStage = (Stage) ((Parent) mouseEvent.getSource()).getScene().getWindow();

                            // Center the new stage in the parent stage
                            stage.setOnShown(e -> {
                                stage.setX(parentStage.getX() + (parentStage.getWidth() / 2) - (stage.getWidth() / 2));
                                stage.setY(parentStage.getY() + (parentStage.getHeight() / 2) - (stage.getHeight() / 2));
                            });

                            stage.showAndWait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    public void deleteCard(int index, boolean isFarm, Player player) {
        // ==== Initialize lists of farmSlots and activeDeckSlots ====
        List<Node> farmSlots = (player == player1) ? farmSlots_1 : farmSlots_2;
        List<Node> activeDeckSlots = (player == player1) ? activeDeckSlots_1 : activeDeckSlots_2;

        // ==== Delete card from player's farm or active deck ====
        if (isFarm) {
            player.getFarm().put(index, Card.createCard(Card.CARD_EMPTY_INDEX));
        } else {
            player.getActiveDeck().put(index, Card.createCard(Card.CARD_EMPTY_INDEX));
        }

        // ==== Delete card GUI ====
        // Get slot node
        Node slot;
        if (isFarm) {
            slot = farmSlots.get(index);
        } else {
            slot = activeDeckSlots.get(index);
        }

        // Get pane, imageview, and label
        Pane pane = (Pane) slot;
        ImageView imageView = null;
        Label label = null;

        for (javafx.scene.Node childPane : pane.getChildren()) {
            if (childPane instanceof ImageView) {
                imageView = (ImageView) childPane;
            } else if (childPane instanceof Label) {
                label = (Label) childPane;
            }
        }

        // Set pane to empty card
        Card empty = CardConstants.createCard(CardConstants.CARD_EMPTY_INDEX);
        if (imageView != null) {
            Image image = new Image(getClass().getResource(empty.getCardPath()).toExternalForm());
            imageView.setImage(image);
        }

        if (label != null) {
            label.setText(empty.getCardName());
        }
    }

    void testFunction() {
        System.out.println("Anjay work letsgo");
    }

    void testPrintSlots() {
        // System.out.println("Farm player 1");
        // for (int i = 0; i < 20; i++) {
        //     String name = player1.getFarm().get(i).getCardName();
        //     if (name.equals("")) {
        //         System.out.println("-");
        //     } else {
        //         System.out.println(name);
        //     }
        // }

        // System.out.println("Active deck player 1");
        // for (int i = 0; i < 6; i++) {
        //     String name = player1.getActiveDeck().get(i).getCardName();
        //     if (name.equals("")) {
        //         System.out.println("-");
        //     } else {
        //         System.out.println(name);
        //     }
        // }

        // System.out.println("Farm player 2");
        // for (int i = 0; i < 20; i++) {
        //     String name = player2.getFarm().get(i).getCardName();
        //     if (name.equals("")) {
        //         System.out.println("-");
        //     } else {
        //         System.out.println(name);
        //     }
        // }

        // System.out.println("Active deck player 2");
        // for (int i = 0; i < 6; i++) {
        //     String name = player2.getActiveDeck().get(i).getCardName();
        //     if (name.equals("")) {
        //         System.out.println("-");
        //     } else {
        //         System.out.println(name);
        //     }
        // }

        System.out.println("farmSlots1");
        System.out.println(farmSlots_1.toString());

        System.out.println("farmSlots2");
        System.out.println(farmSlots_2.toString());

        System.out.println("activeDeckSlots1");
        System.out.println(activeDeckSlots_1.toString());

        System.out.println("activeDeckSlots2");
        System.out.println(activeDeckSlots_2.toString());
    }

    public void setFarmAt(int index, Card card, Player player) {
        // Change data
        player.setFarmAt(index, card);

        // Change GUI
        List<Node> farmSlots = (player == player1) ? farmSlots_1 : farmSlots_2;
        Node slot = farmSlots.get(index);

        Pane pane = (Pane) slot;
        ImageView imageView = null;
        Label label = null;

        for (javafx.scene.Node childPane : pane.getChildren()) {
            if (childPane instanceof ImageView) {
                imageView = (ImageView) childPane;
            } else if (childPane instanceof Label) {
                label = (Label) childPane;
            }
        }

        if (imageView != null) {
            Image image = new Image(getClass().getResource(card.getCardPath()).toExternalForm());
            imageView.setImage(image);
        }

        if (label != null) {
            label.setText(card.getCardName());
        }
    }

    public void setActiveDeckAt(int index, Card card, Player player) {
        // Set data
        player.setActiveDeckAt(index, card);

        // Set GUI
        List<Node> activeDeckSlots = (player == player1) ? activeDeckSlots_1 : activeDeckSlots_2;
        Node slot = activeDeckSlots.get(index);

        Pane pane = (Pane) slot;
        ImageView imageView = null;
        Label label = null;

        for (javafx.scene.Node childPane : pane.getChildren()) {
            if (childPane instanceof ImageView) {
                imageView = (ImageView) childPane;
            } else if (childPane instanceof Label) {
                label = (Label) childPane;
            }
        }

        if (imageView != null) {
            Image image = new Image(getClass().getResource(card.getCardPath()).toExternalForm());
            imageView.setImage(image);
        }

        if (label != null) {
            label.setText(card.getCardName());
        }
    }
    
    public void updateFarm() {
        // Get player
        Player player = (currentFarmView == 1) ? player1 : player2;
        if (currentTurn > 0){
            if (currentPlayer == 1 && currentFarmView == 1){
                myFarm.setStyle("-fx-background-color: #0f5132;");
                enemyFarm.setStyle("-fx-background-color: #4a90e2;");
            }
            if (currentPlayer == 1 && currentFarmView == 2){
                myFarm.setStyle("-fx-background-color: #4a90e2;");
                enemyFarm.setStyle("-fx-background-color: #0f5132;");
            }
            if (currentPlayer == 2 && currentFarmView == 1){
                myFarm.setStyle("-fx-background-color: #4a90e2;");
                enemyFarm.setStyle("-fx-background-color: #0f5132;");
            }
            if (currentPlayer == 2 && currentFarmView == 2){
                myFarm.setStyle("-fx-background-color: #0f5132;");
                enemyFarm.setStyle("-fx-background-color: #4a90e2;");
            }
        }
        // Update farm slots
        for (int i = 0; i < 20; i++) {
            Card card = player.getFarm().get(i);
            if (card != null) {
                setFarmAt(i, card, player);
            }
        }
    }
    
    public void updateActiveDeck() {
        // Get player
        Player player = (currentFarmView == 1) ? player1 : player2;
    
        // Update active deck slots
        for (int i = 0; i < 20; i++) {
            Card card = player.getActiveDeck().get(i);
            if (card != null) {
                setActiveDeckAt(i, card, player);
            }
        }
    }

    public void setGameDataGUI() {
        if (currentTurn == 0){
            storeButton.setDisable(true);
        }
        else{
            storeButton.setDisable(false);
        }
        TurnNumber.setText(Integer.toString(currentTurn));
        Player1_Gold.setText(Integer.toString(player1.getGuldenCount()) + " Gold");
        Player2_Gold.setText(Integer.toString(player2.getGuldenCount()) + " Gold");
        
        Player p = (currentPlayer == 1) ? player1 : player2;
        DeckCount.setText(Integer.toString(p.getDeckCount()) + "/40");
    }

    @FXML
    private AnchorPane Base;
    
    @FXML
    private GridPane Ladang;

    @FXML
    private GridPane Deck;

    @FXML
    private Label TurnNumber;

    @FXML
    private Label Player1_Gold;

    @FXML
    private Label Player2_Gold;

    @FXML
    private Label DeckCount;

    @FXML
    private Group BearAttackArea;

    @FXML
    private Pane TimerPane;
    
    @FXML
    private Label TimerLabel;
    
    @FXML
    private Button NextButton;

    private MediaPlayer mediaPlayer;
    private MediaPlayer mclarenPlayer;

    @FXML
    private Button storeButton;

    @FXML
    private Button myFarm;

    @FXML
    private Button enemyFarm;

    @FXML
    private Button saveButton;

    @FXML
    public void initialize() {
        myFarm.setDisable(true);
        enemyFarm.setDisable(true);
        saveButton.setDisable(true);
        NextButton.setText("START!");
        storeButton.setDisable(true);
        myFarm.setStyle("-fx-background-color: #4a90e2;");
        enemyFarm.setStyle("-fx-background-color: #4a90e2;");

        playBackgroundMusic("/com/tubes2_btc/Pages/Sound/main.m4a");
        // Set player data to DataPasser
        DataPasser dataPasser = DataPasser.getInstance();
        dataPasser.player1 = player1;
        dataPasser.player2 = player2;
        dataPasser.currentPlayer = currentPlayer;
        dataPasser.mainPageController = this;

        // Initialize farm and active deck
        int i = 0;
        for (Node child : Ladang.getChildren()) {
            child.setId("Ladang_" + i);
            initializeSlot(child, i, true, farmSlots_1, activeDeckSlots_1, player1);
            initializeSlot(child, i, true, farmSlots_2, activeDeckSlots_2, player2);
            i++;
        }

        int j = 0;
        for (Node child : Deck.getChildren()) {
            child.setId("Deck_" + j);
            initializeSlot(child, j, false, farmSlots_1, activeDeckSlots_1, player1);
            initializeSlot(child, j, false, farmSlots_2, activeDeckSlots_2, player2);
            j++;
        }

        // Update farm
        updateFarm();
        updateActiveDeck();

        // Update game data GUI
        setGameDataGUI();
    }

    private void playBackgroundMusic(String musicFilePath) {
        try {
            String resourcePath = getClass().getResource(musicFilePath).toString();
            Media media = new Media(resourcePath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set to repeat indefinitely
            mediaPlayer.setVolume(0.5); // Set volume to 50%
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playMcLarenLuWarnaApaBos() {
        try {
            String resourcePath = getClass().getResource("/com/tubes2_btc/Pages/Sound/mclaren.m4a").toString();
            Media media = new Media(resourcePath);
            mclarenPlayer = new MediaPlayer(media);
            mclarenPlayer.setRate(1.1);
            mclarenPlayer.setVolume(1); 
            mclarenPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void startTimer(int duration,Runnable onFinish) {
        Thread timerThread = new Thread(() -> {
            for (int i = duration * 10; i >= 0; i--) {
                int finalI = i;
                Platform.runLater(() -> {
                    this.TimerLabel.setText(String.format("%.1f s", finalI / 10.0));
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(onFinish);
        });
        timerThread.setDaemon(true);
        timerThread.start();
    }

    private void removeTimer() {
        if (this.TimerPane != null) {
            Base.getChildren().remove(this.TimerPane);
            this.TimerPane = null;
        }
        if (this.TimerLabel != null) {
            Base.getChildren().remove(this.TimerLabel);
            this.TimerLabel = null;
        }
    }

    private void removeBearAttackArea(){
        DataPasser dataPasser = DataPasser.getInstance();
        if (dataPasser.stage != null) {
            dataPasser.stage.close();
        }
        if(this.BearAttackArea != null){
            this.Base.getChildren().remove(this.BearAttackArea);
        }
    }

    public synchronized void  manipulateFarm(int draggedIndex, int droppedIndex, boolean draggedIsFarm,String task,Player player){
        if(task.compareTo("delete") == 0){
            deleteCard(droppedIndex, draggedIsFarm, player);
        }
        else if(task.compareTo("move") == 0){
            player.swapSlots(draggedIndex, droppedIndex, player.getFarm(), player.getFarm());
            List<Node> farmSlots = (currentPlayer == 1) ? farmSlots_1 : farmSlots_2;
            Node slot_dragged = farmSlots.get(draggedIndex);
            Node slot_dropped = farmSlots.get(droppedIndex);
            swapNodes(slot_dragged, slot_dropped);
        }
    }

    public void bearAttackHandler(){
        Random rand = new Random();
        // determine whether to let the bear out or no
        // because i use bool, the chance of a bear attack happen is 50%
        boolean attack =  rand.nextBoolean();
        // determine whether the subgrid would be 3x2 or 2x3
        boolean isHorizontal = rand.nextBoolean();
        int width=0,height=0,x=0,y=0;
        if(attack){
            System.out.println("The bear is out! Watch yo ass!");
            int duration = 30 + rand.nextInt(0,31);
            int area = rand.nextInt(1,6);
            NextButton.setDisable(true);
            switch(area){
                case 6:
                    if(isHorizontal){
                        // 3x2 subgrid
                        // set the bound so it wont went out of ladang
                        width=3;
                        height=2;
                        x = rand.nextInt(0, 2);
                        y = rand.nextInt(0, 2);
                    }else{
                        // 2x3 subgrid
                        // set the bound so it wont went out of ladang
                        width=2;
                        height=3;
                        x = rand.nextInt(0, 3);
                        y = rand.nextInt(0, 1);
                    }
                    break;
                case 5:
                    // 5x1 subgrid
                    // set the bound so it wont went out of ladang
                    width=5;
                    height=1;
                    x = 0;
                    y = rand.nextInt(0, 3);
                    break;
                case 4:
                    // 2x2 subgrid
                    // set the bound so it wont went out of ladang
                    width=2;
                    height=2;
                    x = rand.nextInt(0, 2);
                    y = rand.nextInt(0, 1);
                    break;
                case 3:
                    if(isHorizontal){
                        // 3x1 subgrid
                        // set the bound so it wont went out of ladang
                        width=3;
                        height=1;
                        x = rand.nextInt(0, 2);
                        y = rand.nextInt(0, 3);
                    }else{
                        // 1x3 subgrid
                        // set the bound so it wont went out of ladang
                        width=1;
                        height=3;
                        x = rand.nextInt(0, 4);
                        y = rand.nextInt(0, 1);
                    }
                    break;
                case 2:
                    if(isHorizontal){
                        // 2x1 subgrid
                        // set the bound so it wont went out of ladang
                        width=2;
                        height=1;
                        x = rand.nextInt(0, 3);
                        y = rand.nextInt(0, 3);
                    }else{
                        // 1x2 subgrid
                        // set the bound so it wont went out of ladang
                        width=1;
                        height=2;
                        x = rand.nextInt(0, 4);
                        y = rand.nextInt(0, 2);
                    }
                    break;
                case 1:
                    // 1x1 subgrid
                    // set the bound so it wont went out of ladang
                    width=1;
                    height=1;
                    x = rand.nextInt(0, 4);
                    y = rand.nextInt(0, 3);
                    break;
            }
            final int finalWidth = width;
            final int finalHeight = height;
            final int finalX = x;
            final int finalY = y;
            System.out.println("final width "+ finalWidth);
            System.out.println("final height "+ finalHeight);
            System.out.println("final x "+ finalX);
            System.out.println("final y "+ finalY);

            this.BearAttackArea = addDynamicRectangle(100.0*finalWidth, 118.0*finalHeight, x, y);
            addDynamicTimer();
            startTimer(duration,()->{
                // Checking if subgrid is trapped
                if(isHorizontal){
                    int index = finalX + finalY*5;
                    System.out.println(index);
                    for(int i =0;i<finalHeight;i++){
                        for(int j =0;j<finalWidth;j++){
                            Player p = (currentPlayer == 1) ? player1 : player2;
                            if(p.getFarm().get(index+j).isTrapped()){
                                System.out.println("Trap effect activated! Manta Manta Mantap!");
                                Card bearCard = Card.createCard(CardConstants.CARD_BERUANG_INDEX);
                                p.addToActiveDeck(bearCard);
                                updateActiveDeck();
                                removeBearAttackArea();
                                removeTimer();
                                NextButton.setDisable(false);
                                return;
                            }
                        }
                        index += 5;
                    }
                }else{
                    int index = finalX + finalY*5;
                    System.out.println(index);
                    for(int i =0;i<finalHeight;i++){
                        for(int j =0;j<finalWidth;j++){
                            Player p = (currentPlayer == 1) ? player1 : player2;
                            if(p.getFarm().get(index+j).isTrapped()){
                                System.out.println("Trap effect activated! Manta Manta Mantap!");
                                Card bearCard = Card.createCard(CardConstants.CARD_BERUANG_INDEX);
                                p.addToActiveDeck(bearCard);
                                updateActiveDeck();
                                removeBearAttackArea();
                                removeTimer();
                                NextButton.setDisable(false);
                                return;
                            }
                        }
                        index += 5;
                    }
                }

                // Deleting all cards within the subgrid
                if(isHorizontal){
                    int index = finalX + finalY*5;
                    System.out.println(index);
                    for(int i =0;i<finalHeight;i++){
                        for(int j =0;j<finalWidth;j++){
                            System.out.println("Deleting card "+ (index+j));
                            Player p = (currentPlayer == 1) ? player1 : player2;
                            manipulateFarm(0, index+j, true, "delete",p);
                        }
                        index += 5;
                    }
                }else{
                    int index = finalX + finalY*5;
                    System.out.println(index);
                    for(int i =0;i<finalHeight;i++){
                        for(int j =0;j<finalWidth;j++){
                            Player p = (currentPlayer == 1) ? player1 : player2;
                            
                            if (!p.getFarm().get(index+j).isProtected()) {
                                System.out.println(p.getFarm().get(index+j).getCardName() + " " + p.getFarm().get(index+j).isProtected());
                                System.out.println("Deleting card "+ (index+j));
                                manipulateFarm(0, index+j, true, "delete",p);
                            }
                        }
                        index += 5;
                    }
                }
                removeBearAttackArea();
                removeTimer();
                NextButton.setDisable(false);
                testPrintSlots();
            });
        }
    }

    public Group addDynamicRectangle(double width, double height, double x, double y) {
        Group group = new Group();
        Rectangle rectangle = new Rectangle();
        double x_value = x * 105 + 25;
        double y_value = y * 117 + 5;
        rectangle.setArcHeight(5.0);
        rectangle.setArcWidth(5.0);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setMouseTransparent(true);
        rectangle.setHeight(height);
        rectangle.setWidth(width);
        rectangle.setStroke(Color.web("#ce1717"));
        rectangle.setStrokeWidth(5.0);
        rectangle.setX(x_value);
        rectangle.setY(y_value);
        rectangle.setMouseTransparent(true);
        Base.getChildren().add(rectangle);

        Ellipse ellipse = new Ellipse(x_value + width / 2, y_value + height / 2, width / 2, height / 2);
        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStroke(Color.TRANSPARENT);

        Image image = new Image(
                getClass().getResource("/com/tubes2_btc/Pages/Images/AddOns/mclaren.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        group.getChildren().addAll(rectangle, imageView);
        Base.getChildren().add(group);

        PathTransition pathTransition = new PathTransition(Duration.seconds(1), ellipse, imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(PathTransition.INDEFINITE);
        pathTransition.play();

        return group;
    }

    public void addDynamicTimer() {
        Pane timerPane = new Pane();
        
        // Set the properties for the timer pane
        timerPane.setLayoutX(570.0);
        timerPane.setLayoutY(210.0);
        timerPane.setPrefHeight(84.0);
        timerPane.setPrefWidth(147.0);
        timerPane.setStyle("-fx-background-color: white;");
        
        // Create the time left label
        Label timeLabel = new Label("60.0 s");
        timeLabel.setLayoutX(24.0);
        timeLabel.setLayoutY(31.0);
        timeLabel.setStyle("-fx-font-weight: 800;");
        timeLabel.setTextFill(Color.RED);
        timeLabel.setFont(new Font(36.0));
        
        // Create the label for static text "time left"
        Label staticLabel = new Label("time left");
        staticLabel.setLayoutX(14.0);
        staticLabel.setLayoutY(-1.0);
        staticLabel.setFont(new Font(20.0));
        
        // Add labels to the timer pane
        timerPane.getChildren().addAll(timeLabel, staticLabel);
        
        // Add the timer pane to the base layout
        Base.getChildren().add(timerPane);
        
        // Keep a reference to the timer pane and label for further updates
        this.TimerPane = timerPane;
        this.TimerLabel = timeLabel;
    }
    
    // Pop Up Button Handler
    @FXML
    private void nextButtonHandler(ActionEvent event) {
        myFarm.setDisable(false);
        enemyFarm.setDisable(false);
        saveButton.setDisable(false);
        NextButton.setText("NEXT");
        if (currentTurn + 1 == 21) {
            DataPasser dataPasser = DataPasser.getInstance();
            dataPasser.player1Gold = player1.getGuldenCount() + " Gulden";
            dataPasser.player2Gold = player2.getGuldenCount() + " Gulden";
            if (player1.getGuldenCount() > player2.getGuldenCount()) {
                dataPasser.winLabel = "The Winner is Player 1!";
            }
            else if (player1.getGuldenCount() < player2.getGuldenCount()) {
                dataPasser.winLabel = ("The Winner is Player 2");
            }
            else {
                dataPasser.winLabel = ("The game ends in a draw:/");
            }

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/endgame-screen.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));

                // Get the parent stage (assuming the button is within a stage)
                Stage parentStage = (Stage) ((Parent) event.getSource()).getScene().getWindow();

                // Center the new stage in the parent stage
                stage.setOnShown(e -> {
                    stage.setX(parentStage.getX() + (parentStage.getWidth() / 2) - (stage.getWidth() / 2));
                    stage.setY(parentStage.getY() + (parentStage.getHeight() / 2) - (stage.getHeight() / 2));
                });

                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        else {
            try {
                // Set game state variables
                currentTurn++;
                currentPlayer = (currentTurn % 2 == 1) ? 1 : 2;
                currentFarmView = currentPlayer;

            // Update cards
            updateFarm();
            updateActiveDeck();
            setGameDataGUI();

                // Set data passer
                DataPasser dataPasser = DataPasser.getInstance();
                dataPasser.currentPlayer = currentPlayer;

                // Load FXML
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/random-card.fxml"));
                Parent root = fxmlLoader.load();

                // Set main page controller in random card controller
                RandomCardController r = fxmlLoader.getController();
                r.setMainPageController(this);

                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));

                // Get the parent stage (assuming the button is within a stage)
                Stage parentStage;
                if (event != null) {
                    parentStage = (Stage) ((Parent) event.getSource()).getScene().getWindow();
                } else {
                    parentStage = (Stage) Base.getScene().getWindow();
                }

                // Center the new stage in the parent stage
                stage.setOnShown(e -> {
                    stage.setX(parentStage.getX() + (parentStage.getWidth() / 2) - (stage.getWidth() / 2));
                    stage.setY(parentStage.getY() + (parentStage.getHeight() / 2) - (stage.getHeight() / 2));
                });

                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void myFarmHandler(ActionEvent event) {
        if (currentFarmView == currentPlayer) return;

        // Change farm view
        currentFarmView = currentPlayer;

        // Update farm
        updateFarm();
    }

    @FXML
    private void opponentFarmHandler(ActionEvent event) {
        if (currentFarmView != currentPlayer) return;

        // Change farm view
        currentFarmView = (currentPlayer == 1) ? 2 : 1;

        // Update farm
        updateFarm();
    }

    @FXML
    private void storeButtonHandler(ActionEvent event) {
        if (currentTurn > 0){
            DataPasser dataPasser = DataPasser.getInstance();
            dataPasser.currentPlayer = currentPlayer;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/store-page.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));

                // Get the parent stage (assuming the button is within a stage)
                Stage parentStage = (Stage) ((Parent) event.getSource()).getScene().getWindow();

                // Center the new stage in the parent stage
                stage.setOnShown(e -> {
                    stage.setX(parentStage.getX() + (parentStage.getWidth() / 2) - (stage.getWidth() / 2));
                    stage.setY(parentStage.getY() + (parentStage.getHeight() / 2) - (stage.getHeight() / 2));
                });

                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void saveStateButtonHandler(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/save.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));

            // Set main page controller
            SaveStateController s = fxmlLoader.getController();
            s.setMainPageController(this);

            // Get the parent stage (assuming the button is within a stage)
            Stage parentStage = (Stage) ((Parent) event.getSource()).getScene().getWindow();

            // Center the new stage in the parent stage
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
    private void loadStateButtonHandler(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/load.fxml"));
            Parent root = fxmlLoader.load();

            // Set main page controller
            LoadStateController l = fxmlLoader.getController();
            l.setMainPageController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));

            // Get the parent stage (assuming the button is within a stage)
            Stage parentStage = (Stage) ((Parent) event.getSource()).getScene().getWindow();

            // Center the new stage in the parent stage
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
    private void loadPluginButtonHandler(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/plugin.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));

            // Get the parent stage (assuming the button is within a stage)
            Stage parentStage = (Stage) ((Parent) event.getSource()).getScene().getWindow();

            // Center the new stage in the parent stage
            stage.setOnShown(e -> {
                stage.setX(parentStage.getX() + (parentStage.getWidth() / 2) - (stage.getWidth() / 2));
                stage.setY(parentStage.getY() + (parentStage.getHeight() / 2) - (stage.getHeight() / 2));
            });

            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}