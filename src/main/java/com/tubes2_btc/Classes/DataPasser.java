package com.tubes2_btc.Classes;

import javafx.scene.image.Image;
import java.util.List;

public class DataPasser {
    // DataPasser is a singleton class that is used to pass data between activities

    // DataPasser attributes and methods
    private static DataPasser instance;

    private DataPasser() {}

    public static synchronized DataPasser getInstance() {
        if (instance == null) {
            instance = new DataPasser();
        }
        return instance;
    }

    // ======== Shared data =========
    // Player data
    public Player player1;
    public Player player2;

    // Random card data
    List<Card> randomCards;

    // Info Card Temporary Variable
    public Card infoCard;

    // Store Temporary Variable
    public Store storeTemp;
    public Image imageTemp;
    public String labelTemp;
    public double productPrice;
    public int productQuantity;
}
