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

public class MainPageController {
    @FXML
    private GridPane Ladang;

    @FXML
    private GridPane Deck;

    @FXML
    public void initialize() {
        Random random = new Random();

        int i = 0;
        for (Node child : Ladang.getChildren()) {
            Pane pane = (Pane) child;
            ImageView imageView = null;
            Label label = null;

            for (javafx.scene.Node childPane : pane.getChildren()) {
                if (childPane instanceof ImageView) {
                    imageView = (ImageView) childPane;
                } else if (childPane instanceof Label) {
                    label = (Label) childPane;
                }
            }

            // Get random card
            int rand = random.nextInt(25);
            Card randomCard = Card.createCard(rand);

            if (imageView != null) {
                Image image = new Image(getClass().getResource(randomCard.getCardPath()).toExternalForm());
                imageView.setImage(image);
            }

            if (label != null) {
                label.setText(randomCard.getCardName());
            }

            child.setId("Ladang_" + i);
            child.setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Dragboard db = child.startDragAndDrop(TransferMode.ANY);

                    ClipboardContent content = new ClipboardContent();
                    content.putString(child.getId());
                    db.setContent(content);

                    System.out.println("Drag detected for: " + child.getId());
                    mouseEvent.consume();
                }
            });

            child.setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent dragEvent) {
                    if (dragEvent.getGestureSource() != child && dragEvent.getDragboard().hasString()) {
                        dragEvent.acceptTransferModes(TransferMode.MOVE);
                    }
                    System.out.println("Drag over for: " + child.getId());
                    dragEvent.consume();
                }
            });

            child.setOnDragDropped(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent dragEvent) {
                    Dragboard db = dragEvent.getDragboard();
                    boolean success = false;
                    if (db.hasString()) {
                        System.out.println("Drag dropped for: " + child.getId());
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