package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.DataPasser;
import com.tubes2_btc.Classes.Product;
import com.tubes2_btc.Classes.Store;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;

public class StorePopUpController {

    @FXML
    private ImageView imageView;

    @FXML
    private Text labelText1;

    @FXML
    private Button kembali;

    @FXML
    public void initialize() {
        DataPasser dataPasser = DataPasser.getInstance();
        imageView.setImage(dataPasser.imageTemp);
        labelText1.setText(dataPasser.labelTemp);
    }

    @FXML
    private void handleClickedKembali(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
