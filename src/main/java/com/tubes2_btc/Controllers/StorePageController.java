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
import javafx.scene.layout.VBox;
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

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    private Store store;
    private int currentPage = 0;
    private static final int ITEMS_PER_PAGE = 6;

    @FXML
    public void initialize() {
        // Initialize the list of products
        List<Product> products = new ArrayList<>();
        products.add(new Product(CardConstants.CARD_DAGING_KUDA, CardConstants.CARD_DAGING_KUDA_PATH, 1000, 10, 14, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_SIRIP_HIU, CardConstants.CARD_SIRIP_HIU_PATH, 2000, 5, 20, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_STROBERI, CardConstants.CARD_STROBERI_PATH, 1500, 3, 4, Product.PRODUCT_HERBIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_JAGUNG, CardConstants.CARD_JAGUNG_PATH, 500, 8, 3, Product.PRODUCT_HERBIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_SUSU, CardConstants.CARD_SUSU_PATH, 500, 8, 10, Product.PRODUCT_HERBIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_TELUR, CardConstants.CARD_TELUR_PATH, 500, 8, 5, Product.PRODUCT_HERBIVORE_FOOD));

        // Create store
        store = new Store(products);
        initializeStore(Toko, store.getProducts());
    }

    private List<Product> generateProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(CardConstants.CARD_SIRIP_HIU, CardConstants.CARD_SIRIP_HIU_PATH, 500, 12, 20, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_SUSU, CardConstants.CARD_SUSU_PATH, 100, 4, 10, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_DAGING_DOMBA, CardConstants.CARD_DAGING_DOMBA_PATH, 120, 6, 12, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_DAGING_KUDA, CardConstants.CARD_DAGING_KUDA_PATH, 150, 8, 14, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_TELUR, CardConstants.CARD_TELUR_PATH, 50, 2, 5, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_DAGING_BERUANG, CardConstants.CARD_DAGING_BERUANG_PATH, 150, 8, 25, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_JAGUNG, CardConstants.CARD_JAGUNG_PATH, 500, 8, 3, Product.PRODUCT_HERBIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_LABU, CardConstants.CARD_LABU_PATH, 500, 8, 5, Product.PRODUCT_HERBIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_STROBERI, CardConstants.CARD_STROBERI_PATH, 1500, 3, 4, Product.PRODUCT_HERBIVORE_FOOD));
        return products;
    }

    private void initializeStore(GridPane tokoPane, List<Product> products) {
        int productIndex = currentPage * ITEMS_PER_PAGE;
        int displayedItems = 0;

        for (Node node : tokoPane.getChildren()) {
            if (node instanceof Pane) {
                Pane outerPane = (Pane) node;
                if (displayedItems < ITEMS_PER_PAGE && productIndex < products.size()) {
                    Product product = products.get(productIndex);

                    VBox imagePane = (VBox) outerPane.lookup("#imagePane");
                    VBox detailsPane = (VBox) outerPane.lookup("#detailsPane");

                    ImageView imageView = (ImageView) imagePane.lookup("#image");
                    Label productLabel = (Label) imagePane.lookup("#labelNama");
                    Label priceLabel = (Label) detailsPane.lookup("#labelHarga");
                    Label quantityLabel = (Label) detailsPane.lookup("#labelJumlah");

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
                    displayedItems++;
                } else {
                    outerPane.setVisible(false);
                }
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

    @FXML
    private void handleNext(ActionEvent event) {
        if (currentPage < Math.ceil((double) store.getProducts().size() / ITEMS_PER_PAGE) - 1) {
            currentPage++;
            updatePaginationButtons();
            initializeStore(Toko, store.getProducts());
        }
    }

    @FXML
    private void handlePrevious(ActionEvent event) {
        if (currentPage > 0) {
            currentPage--;
            updatePaginationButtons();
            initializeStore(Toko, store.getProducts());
        }
    }

    private void updatePaginationButtons() {
        int totalItems = store.getProducts().size();
        int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);

        previousButton.setDisable(currentPage <= 0);
        nextButton.setDisable(currentPage >= totalPages - 1);
    }

    public void addNewProductToStore(Product product) {
        store.addProduct(product);
        updatePaginationButtons();
        initializeStore(Toko, store.getProducts());
    }
}
