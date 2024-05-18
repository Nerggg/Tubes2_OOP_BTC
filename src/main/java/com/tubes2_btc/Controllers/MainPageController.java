package com.tubes2_btc.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public class MainPageController {
    @FXML
    private GridPane Ladang;

    @FXML
    private GridPane Deck;

    @FXML
    public void initialize() {
        int i = 0;
        for (Node child : Ladang.getChildren()) {
            child.setId("Ladang_" + i);
            child.setOnDragDetected(event -> {
                System.out.println("Drag detected for: " + child.getId());
            });

            child.setOnDragOver(event -> {
                event.acceptTransferModes(TransferMode.ANY);
                event.consume();
                System.out.println("Drag over for: " + child.getId());
            });

            child.setOnDragDropped(event -> {
                System.out.println("Drag dropped for: " + child.getId());
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

