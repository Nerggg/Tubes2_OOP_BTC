package com.tubes2_btc.Classes;

import com.tubes2_btc.Controllers.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
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
    // Main page controller
    public MainPageController mainPageController;

    public Stage stage;

    // Player data
    public Player player1;
    public Player player2;
    public int currentPlayer;
    public int currentFarmView;

    // Random card data
    List<Card> randomCards;

    // Info Card Temporary Variable
    public Card infoCard;
    public int indexCard;

    // Store Temporary Variable
    public Store storeTemp;
    public Image imageTemp;
    public String labelTemp;
    public Product productTemp;
    public int productPrice;
    public int productQuantity;
    public int productStoreQuantity;

    // Plugin Stuffs
    public File jarFile;

    // Endgame Stuffs
    public String player1Gold;
    public String player2Gold;
    public String winLabel;
}
