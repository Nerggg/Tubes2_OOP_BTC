package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.CardConstants;
import com.tubes2_btc.Classes.DataPasser;
import com.tubes2_btc.Classes.Product;
import com.tubes2_btc.Classes.Store;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.StageStyle;

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

    private static Store store;
    private int currentPage = 0;
    private static final int ITEMS_PER_PAGE = 6;

    @FXML
    public void initialize() {
        // Initialize the list of products
        List<Product> products = generateProducts();
        // Create store
        store = new Store(products, 8);
        initializeStore(Toko, store.getProducts());
        updatePaginationButtons();
    }

    public static void initializeStore() {
        if (store == null) {
            // Initialize the list of products
            List<Product> products = generateProducts();
            // Create store
            store = new Store(products, 8);
        }
    }

    private static List<Product> generateProducts() {
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
                            imageView.setId("productImage");
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

                    // Get product count from store
                    int productCount = store.getProductCount(product.getCardName());
                    if (quantityLabel != null) {
                        quantityLabel.setText(String.valueOf(productCount));
                    }

                    // Check product quantity
                    if (productCount == 0) {
                        ColorAdjust colorAdjust = new ColorAdjust();
                        colorAdjust.setBrightness(-0.5);
                        outerPane.setEffect(colorAdjust);
                        outerPane.setDisable(true);
                    } else {
                        outerPane.setEffect(null);
                        outerPane.setDisable(false);
                    }

                    outerPane.setVisible(true);
                    outerPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            DataPasser dataPasser = DataPasser.getInstance();
//                            dataPasser.imageTemp =
//                            dataPasser.labelTemp =

                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/store-popup.fxml"));
                                Parent root = fxmlLoader.load();
                                Stage stage = new Stage();
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.setScene(new Scene(root));

                                // Get the parent stage (assuming the button is within a stage)
                                Stage parentStage = (Stage) ((Parent) mouseEvent.getSource()).getScene().getWindow();

                                // Center the new stage in the parent stage
                                stage.setOnShown(e -> {
                                    stage.setX(parentStage.getX() + (parentStage.getWidth() / 2) - (stage.getWidth() / 2));
                                    stage.setY(parentStage.getY() + (parentStage.getHeight() / 2) - (stage.getHeight() / 2));
                                });

                                stage.showAndWait();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

                    productIndex++;
                    displayedItems++;
                } else {
                    outerPane.setVisible(false);
                }
            }
        }
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

    public static void addNewProductToStore(Product product, int jumlah) {
        initializeStore();
        store.addProduct(product, jumlah);
    }

    public static Store getStore() {
        return store;
    }
}
    