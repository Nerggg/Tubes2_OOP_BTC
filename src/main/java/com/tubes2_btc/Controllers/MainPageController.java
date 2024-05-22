package com.tubes2_btc.Controllers;
import com.tubes2_btc.Classes.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;
import com.tubes2_btc.Classes.Card;
import com.tubes2_btc.Classes.Player;
import java.util.Map;
import java.util.Random;

public class MainPageController {
    // Misc. variables
    private int draggedCard;
    private boolean draggedIsFarm;

    Player player1 = new Player();
    Player player2 = new Player();

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

    public void initializeSlot(Node child, int i, boolean isFarm, List<Node> farmSlots, List<Node> activeDeckSlots, Player player) {
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

        // Set image and name for farm slot
        Card card;
        if (isFarm) 
            card = player.getFarm().get(i);
        else 
            card = player.getActiveDeck().get(i);
        
        if (card != null) {
            if (imageView != null) {
                Image image = new Image(getClass().getResource(card.getCardPath()).toExternalForm());
                imageView.setImage(image);
            }

            if (label != null) {
                label.setText(card.getCardName());
            }
        }

        // Set farm slot event handlers
        child.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // Set currently dragged card
                int underscoreIndex = child.getId().indexOf("_");
                draggedCard = Integer.parseInt(child.getId().substring(underscoreIndex + 1));

                String slot_type = child.getId().substring(0, underscoreIndex);
                
                if (slot_type.equals("Ladang"))    draggedIsFarm = true;
                else                                        draggedIsFarm = false;

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
                int droppedCard = Integer.parseInt(child.getId().substring(underscoreIndex + 1));

                String slot_type = child.getId().substring(0, underscoreIndex);
                
                boolean droppedIsFarm;
                if (slot_type.equals("Ladang"))    droppedIsFarm = true;
                else                                        droppedIsFarm = false;

                // Swap farm slots
                Map<Integer, Card> cont1, cont2;

                cont1 = (draggedIsFarm) ? player.getFarm() : player.getActiveDeck();
                cont2 = (droppedIsFarm) ? player.getFarm() : player.getActiveDeck();

                player.swapSlots(draggedCard, droppedCard, cont1, cont2);

                // Swap images and names
                Node slot_dragged;
                if (draggedIsFarm)  slot_dragged = farmSlots.get(draggedCard);
                else                slot_dragged = activeDeckSlots.get(draggedCard);
                
                swapNodes(child, slot_dragged);

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
    }

    public void deleteCard(int index, boolean isFarm, Player player) {
        // ==== Initialize lists of farmSlots and activeDeckSlots ====
        List<Node> farmSlots = (player == player1) ? farmSlots_1 : farmSlots_2;
        List<Node> activeDeckSlots = (player == player1) ? activeDeckSlots_1 : activeDeckSlots_2;

        // ==== Delete card from player's farm or active deck ====
        if (isFarm) {
            player.getFarm().remove(index);
        } else {
            player.getActiveDeck().remove(index);
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
    
    @FXML
    private AnchorPane Base;
    
    @FXML
    private GridPane Ladang;

    @FXML
    private GridPane Deck;

    @FXML
    public void initialize() {
        // Set player data to DataPasser
        DataPasser dataPasser = DataPasser.getInstance();
        dataPasser.player1 = player1;
        dataPasser.player2 = player2;

        // Initialize farm and active deck
        int i = 0;
        for (Node child : Ladang.getChildren()) {
            child.setId("Ladang_" + i);
            initializeSlot(child, i, true, farmSlots_1, activeDeckSlots_1, player1);
            i++;
        }

        int j = 0;
        for (Node child : Deck.getChildren()) {
            child.setId("Deck_" + j);
            initializeSlot(child, j, false, farmSlots_1, activeDeckSlots_1, player1);
            j++;
        }
        bearAttackHandler();
    }

    public void bearAttackHandler(){
        Random rand = new Random();
        // determine whether to let the bear out or no
        // because i use bool, the chance of a bear attack happen is 50%
        boolean attack = rand.nextBoolean();
        // determine whether the subgrid would be 3x2 or 2x3
        boolean type = rand.nextBoolean();
        if(attack){
            if(type){
                // 3x2 subgrid
                // set the bound so it wont went out of ladang
                int x = rand.nextInt(0, 2);
                int y = rand.nextInt(0, 2);
                addDynamicRectangle(100.0*3, 118.0*2, x, y);
            }else{
                // 2x3 subgrid
                // set the bound so it wont went out of ladang
                int x = rand.nextInt(0, 3);
                int y = rand.nextInt(0, 1);
                addDynamicRectangle(100.0*2, 118.0*3, x, y);
            }
        }else{
            addDynamicRectangle(0.0, 0.0, 0.0, 0.0);
        }

    }
     public void addDynamicRectangle(double width, double height, double x, double y) {
        Rectangle rectangle = new Rectangle();
        // offset
        double x_value = x * 105 + 25;
        double y_value = y * 117 + 5;
        rectangle.setArcHeight(5.0);
        rectangle.setArcWidth(5.0);
        rectangle.setFill(Color.web("#c4040400"));
        rectangle.setHeight(height);
        rectangle.setWidth(width);
        rectangle.setStroke(Color.web("#ce1717"));
        rectangle.setStrokeWidth(5.0);
        rectangle.setX(x_value);
        rectangle.setY(y_value);
        Base.getChildren().add(rectangle);
    }
    // Pop Up Button Handler
    @FXML
    private void nextButtonHandler(ActionEvent event) {
        try {
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
    private void storeButtonHandler(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/store_page.fxml"));
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

    @FXML
    private void saveStateButtonHandler(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/save.fxml"));
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

    @FXML
    private void loadStateButtonHandler(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/load.fxml"));
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