package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DeckCardInfoController {

    @FXML
    private Button kembali;

    @FXML
    private Button jual;

    @FXML
    private ImageView imageView;

    @FXML
    private Text labelText1;

    @FXML
    private Text labelText2;

    @FXML
    private Text labelValue1;

    @FXML
    private Text labelValue2;

    @FXML
    private Text labelItemAktif;

    @FXML
    private Text labelItemAktifValue;

    private Product currentProduct;

    @FXML
    private void handleClickedKembali(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleClickedJual(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/sell-confirmation.fxml"));
            Parent root = fxmlLoader.load();

            // Pass the product information to the sell confirmation controller
            SellConfirmationController controller = fxmlLoader.getController();
            controller.setProduct(currentProduct);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));

            Stage parentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setOnShown(e -> {
                stage.setX(parentStage.getX() + (parentStage.getWidth() / 2) - (stage.getWidth() / 2));
                stage.setY(parentStage.getY() + (parentStage.getHeight() / 2) - (stage.getHeight() / 2));
            });

            stage.showAndWait();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Tombol 'Jual' ditekan.");
    }

    @FXML
    public void initialize() {
        DataPasser dataPasser = DataPasser.getInstance();
        if (dataPasser.infoCard.getClass().getSimpleName().equals("Product")) {
            currentProduct = (Product) dataPasser.infoCard;
            labelText1.setText(currentProduct.getCardName());
            labelText2.setText("Berat:");
            labelValue1.setText(Integer.toString(currentProduct.getHarvestAge()));
            labelValue2.setText("(" + Integer.toString(currentProduct.getHarvestAge()) + ")");
            labelItemAktifValue.setText(currentProduct.getCardActive());
            Image image = new Image(getClass().getResource(currentProduct.getCardPath()).toExternalForm());
            imageView.setImage(image);
        }
    }
}
