package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.DataPasser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EndgameController {
    @FXML
    private Label player1Gold;
    @FXML
    private Label player2Gold;
    @FXML
    private Label win;

    public void initialize() {
        DataPasser dataPasser = DataPasser.getInstance();
        player1Gold.setText(dataPasser.player1Gold);
        player2Gold.setText(dataPasser.player2Gold);
        win.setText(dataPasser.winLabel);
    }
}
