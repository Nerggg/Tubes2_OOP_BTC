package com.tubes2_btc.Components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Card extends Pane {
    // Private attributes
    private String imageURL;
    private String name;

    // Constructor
    public Card() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("card.fxml"));
        fxmlLoader.setRoot(this);

        // Set controller
        fxmlLoader.setController(this);

        // Load fxml
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Card(String imageURL, String name) {
        this();
        this.setImageURL(imageURL);
        this.setName(name);
    }

    // Getter and setter
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setName(String name) {
        this.name = name;
    }
}
