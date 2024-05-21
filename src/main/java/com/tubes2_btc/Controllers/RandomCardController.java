package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.CardConstants;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.Random;

public class RandomCardController {
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private Button randomizeButton;

    private final String[] paths = {
            CardConstants.CARD_HIU_PATH,
            CardConstants.CARD_SAPI_PATH,
            CardConstants.CARD_DOMBA_PATH,
            CardConstants.CARD_KUDA_PATH,
            CardConstants.CARD_AYAM_PATH,
            CardConstants.CARD_BERUANG_PATH,
            CardConstants.CARD_BIJI_LABU_PATH,
            CardConstants.CARD_BIJI_JAGUNG_PATH,
            CardConstants.CARD_BIJI_STROBERI_PATH,
            CardConstants.CARD_LABU_PATH,
            CardConstants.CARD_JAGUNG_PATH,
            CardConstants.CARD_STROBERI_PATH,
            CardConstants.CARD_SUSU_PATH,
            CardConstants.CARD_TELUR_PATH,
            CardConstants.CARD_SIRIP_HIU_PATH,
            CardConstants.CARD_DAGING_KUDA_PATH,
            CardConstants.CARD_DAGING_DOMBA_PATH,
            CardConstants.CARD_DAGING_BERUANG_PATH,
            CardConstants.CARD_ACCELERATE_PATH,
            CardConstants.CARD_DELAY_PATH,
            CardConstants.CARD_INSTANT_HARVEST_PATH,
            CardConstants.CARD_DESTROY_PATH,
            CardConstants.CARD_PROTECT_PATH,
            CardConstants.CARD_TRAP_PATH
    };

    private final Random random = new Random();

    @FXML
    private void initialize() {
        randomizeButton.setOnAction(event -> changeImages());
    }

    private void changeImages() {
        URL imageUrl1 = getClass().getResource(paths[random.nextInt(0, paths.length-1)]);
        if (imageUrl1 != null) {
            Image imageNew1 = new Image(imageUrl1.toExternalForm());
            imageView1.setImage(imageNew1);
        }
        URL imageUrl2 = getClass().getResource(paths[random.nextInt(0, paths.length-1)]);
        if (imageUrl2 != null){
            Image imageNew2 = new Image(imageUrl2.toExternalForm());
            imageView2.setImage(imageNew2);
        }
        URL imageUrl3 = getClass().getResource(paths[random.nextInt(0, paths.length-1)]);
        if (imageUrl3 != null){
            Image imageNew3 = new Image(imageUrl3.toExternalForm());
            imageView3.setImage(imageNew3);
        }
        URL imageUrl4 = getClass().getResource(paths[random.nextInt(0, paths.length-1)]);
        if (imageUrl4 != null){
            Image imageNew4 = new Image(imageUrl4.toExternalForm());
            imageView4.setImage(imageNew4);
        }
//        imageView2.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
//        imageView3.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
//        imageView4.setImage(new Image(paths[random.nextInt(0, paths.length-1)]));
    }
}
