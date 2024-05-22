package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.CardConstants;
import com.tubes2_btc.Classes.Product;
import com.tubes2_btc.Classes.Store;
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
import java.util.ArrayList;
import java.util.List;

public class StorePageController {

    @FXML
    private Button kembali;

    @FXML
    private AnchorPane main;

    @FXML
    private GridPane Toko;

    private Store store;

    @FXML
    public void initialize() {
        // Initialize the list of products
        List<Product> products = new ArrayList<>();
        products.add(new Product(CardConstants.CARD_DAGING_KUDA, CardConstants.CARD_DAGING_KUDA_PATH, 1000, 10, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_SIRIP_HIU, CardConstants.CARD_SIRIP_HIU_PATH, 2000, 5, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_STROBERI, CardConstants.CARD_STROBERI_PATH, 1500, 3, Product.PRODUCT_HERBIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_JAGUNG, CardConstants.CARD_JAGUNG_PATH, 500, 8, Product.PRODUCT_HERBIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_SUSU, CardConstants.CARD_SUSU_PATH, 500, 8, Product.PRODUCT_HERBIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_TELUR, CardConstants.CARD_TELUR_PATH, 500, 8, Product.PRODUCT_HERBIVORE_FOOD));

        // Create store
        store = new Store(products);
        initializeStore(Toko, store.getProducts());
    }

    private void initializeStore(GridPane tokoPane, List<Product> products) {
        int productIndex = 0;

        for (Product product : products) {
            if (productIndex < tokoPane.getChildren().size() && product.getAddedWeight() != 0) {
                Pane outerPane = (Pane) tokoPane.getChildren().get(productIndex);

                // Nested panes to reach the ImageView and Labels
                Pane innerPane = (Pane) outerPane.getChildren().get(0);
                Pane deepestPane = (Pane) innerPane.getChildren().get(0);

                ImageView imageView = null;
                Label productLabel = null;
                Label priceLabel = null;
                Label quantityLabel = null;

                for (Node node : deepestPane.getChildren()) {
                    if (node instanceof ImageView) {
                        imageView = (ImageView) node;
                    } else if (node instanceof Label) {
                        Label tempLabel = (Label) node;
                        if (tempLabel.getText().equals("Nama")) {
                            productLabel = tempLabel;
                        } else if (tempLabel.getText().equals("Gd.")) {
                            priceLabel = (Label) deepestPane.getChildren().get(deepestPane.getChildren().indexOf(tempLabel) + 1);
                        } else if (tempLabel.getText().equals("Jumlah")) {
                            quantityLabel = (Label) deepestPane.getChildren().get(deepestPane.getChildren().indexOf(tempLabel) + 1);
                        }
                    }
                }

                if (imageView != null) {
                    URL imageUrl = getClass().getResource(product.getCardPath());
                    if (imageUrl != null) {
                        Image imageNew = new Image(imageUrl.toExternalForm());
                        imageView.setImage(imageNew);
                    } else {
                        System.err.println("Gambar tidak ditemukan: " + product.getCardPath());
                    }
                }

                if (productLabel != null) {
                    productLabel.setText(product.getCardName());
                }
                if (priceLabel != null) {
                    priceLabel.setText("Gd. " + product.getSellPrice());
                }
                if (quantityLabel != null) {
                    quantityLabel.setText(String.valueOf(product.getAddedWeight()));
                }

                outerPane.setVisible(true);
                outerPane.setOnMouseClicked(event -> handlePaneClicked(outerPane));

                productIndex++;
            } else if (productIndex < tokoPane.getChildren().size()) {
                Pane outerPane = (Pane) tokoPane.getChildren().get(productIndex);
                outerPane.setVisible(false);
            }
        }
    }

    private void handlePaneClicked(Pane pane) {
        System.out.println("Pane clicked: " + pane);
    }

    @FXML
    private void handleClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void addNewProductToStore(Product product) {
        store.addProduct(product);
        initializeStore(Toko, store.getProducts());
    }
}
