package com.tubes2_btc.Controllers;
import com.tubes2_btc.Classes.*;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.Dragboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class MainPageController {
    // Misc. variables
    private int draggedCard;
    private List<Node> farmSlots = new ArrayList<>();

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
    
    @FXML
    private GridPane Ladang;

    @FXML
    private GridPane Deck;

    @FXML
    public void initialize() {
        Player player1 = new Player();

        int i = 0;
        for (Node child : Ladang.getChildren()) {
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
            farmSlots.add(child);

            // Set image and name for farm slot
            Card card = player1.getFarm().get(i);
            if (card != null) {
                if (imageView != null) {
                    Image image = new Image(getClass().getResource(player1.getFarm().get(i).getCardPath()).toExternalForm());
                    imageView.setImage(image);
                }
    
                if (label != null) {
                    label.setText(player1.getFarm().get(i).getCardName());
                }
            }

            // Set farm slot event handlers
            child.setId("Ladang_" + i);
            child.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // Set currently dragged card
                    int underscoreIndex = child.getId().indexOf("_");
                    draggedCard = Integer.parseInt(child.getId().substring(underscoreIndex + 1));

                    // Event handler
                    Dragboard db = child.startDragAndDrop(TransferMode.ANY);

                    ClipboardContent content = new ClipboardContent();
                    content.putString(child.getId());
                    db.setContent(content);

                    // System.out.println("Drag detected for: " + child.getId());
                    mouseEvent.consume();
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

                    // Swap farm slots
                    player1.swapFarm(draggedCard, droppedCard);

                    // Swap images and names
                    Node slot_dragged = farmSlots.get(draggedCard);
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
            i++;
        }

        int j = 0;
        for (Node child : Deck.getChildren()) {
            child.setId("Deck_" + j);
            j++;
        }
    }
}