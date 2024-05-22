package com.tubes2_btc.Classes;

import java.util.List;
import java.util.ArrayList;

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
}
