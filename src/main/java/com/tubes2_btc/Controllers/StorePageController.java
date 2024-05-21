package com.tubes2_btc.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.net.URL;

public class StorePageController {

    @FXML
    private Button kembali;

    @FXML
    private AnchorPane main;

    @FXML
    private GridPane Toko;

    @FXML
    public void initialize() {
        main.setStyle("-fx-background-color: #ADD8E6;");

        for (Node child : Toko.getChildren()) {
            if (child instanceof Pane) {
                Pane outerPane = (Pane) child;

                // Nested panes to reach the ImageView and Labels
                Pane innerPane = (Pane) outerPane.getChildren().get(0);
                Pane deepestPane = (Pane) innerPane.getChildren().get(0);

                ImageView imageView = null;
                Label itemLabel = null;
                Label hargaLabel = null;
                Label jumlahLabel = null;

                for (Node node : deepestPane.getChildren()) {
                    if (node instanceof ImageView) {
                        imageView = (ImageView) node;
                    } else if (node instanceof Label) {
                        Label tempLabel = (Label) node;
                        if ("Label".equals(tempLabel.getText())) {
                            itemLabel = tempLabel;
                        } else if ("Harga".equals(tempLabel.getText())) {
                            hargaLabel = tempLabel;
                        } else if ("Jumlah".equals(tempLabel.getText())) {
                            jumlahLabel = tempLabel;
                        }
                    }
                }

                // Find the Labels outside of the deepest Pane
                for (Node node : innerPane.getChildren()) {
                    if (node instanceof Label) {
                        Label tempLabel = (Label) node;
                        if ("Harga".equals(tempLabel.getText())) {
                            hargaLabel = tempLabel;
                        } else if ("Jumlah".equals(tempLabel.getText())) {
                            jumlahLabel = tempLabel;
                        }
                    }
                }

                // Output debug
                System.out.println("ImageView: " + (imageView != null));
                System.out.println("Label: " + (itemLabel != null));
                System.out.println("HargaLabel: " + (hargaLabel != null));
                System.out.println("JumlahLabel: " + (jumlahLabel != null));

                if (imageView != null) {
                    URL imageUrl = getClass().getResource("/com/tubes2_btc/Pages/Images/Hewan/hiu darat.png");
                    if (imageUrl != null) {
                        Image imageNew = new Image(imageUrl.toExternalForm());
                        imageView.setImage(imageNew);
                    } else {
                        System.err.println("Gambar tidak ditemukan: /com/tubes2_btc/Pages/Images/Hewan/hiu darat.png");
                    }
                }

                if (itemLabel != null) {
                    itemLabel.setText("Hiu");
                }
                if (hargaLabel != null) {
                    hargaLabel.setText("Rp. 1000");
                }
                if (jumlahLabel != null) {
                    jumlahLabel.setText("10");
                }
            }
        }
    }

    @FXML
    private void handleClicked(ActionEvent event) {
        // Tambahkan logika untuk menangani aksi tombol kembali
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
