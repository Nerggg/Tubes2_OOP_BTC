package com.tubes2_btc.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.tubes2_btc.Classes.CardConstants;
import com.tubes2_btc.Classes.DataPasser;
import com.tubes2_btc.Classes.Player;
import com.tubes2_btc.Classes.Product;
import com.tubes2_btc.Classes.Store;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StorePageController {

    @FXML
    private Button kembali;

    @FXML
    private GridPane Toko;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label playerMoneyLabel;

    private static Store store = new Store(generateProducts(), 0);
    private int currentPage = 0;
    private static final int ITEMS_PER_PAGE = 6;

    @FXML
    public void initialize() {
        // Initialize the list of products
        initializeStore(Toko, this.store.getProducts());
        updatePaginationButtons();
        updatePlayerMoneyLabel();
    }

    private void updatePlayerMoneyLabel() {
        DataPasser dataPasser = DataPasser.getInstance();
        Player currentPlayer = (dataPasser.currentPlayer == 1) ? dataPasser.player1 : dataPasser.player2;
        Platform.runLater(() -> playerMoneyLabel.setText("Gulden: " + String.valueOf(currentPlayer.getGuldenCount())));
    }

    public static void initializeStore() {
        // Method for initializing the store if needed
    }

    private static List<Product> generateProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(CardConstants.CARD_SIRIP_HIU, CardConstants.CARD_SIRIP_HIU_PATH, 500, 12, 20, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_SUSU, CardConstants.CARD_SUSU_PATH, 100, 4, 10, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_DAGING_DOMBA, CardConstants.CARD_DAGING_DOMBA_PATH, 120, 6, 12, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_DAGING_KUDA, CardConstants.CARD_DAGING_KUDA_PATH, 150, 8, 14, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_TELUR, CardConstants.CARD_TELUR_PATH, 50, 2, 5, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_DAGING_BERUANG, CardConstants.CARD_DAGING_BERUANG_PATH, 500, 8, 25, Product.PRODUCT_CARNIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_JAGUNG, CardConstants.CARD_JAGUNG_PATH, 150, 8, 3, Product.PRODUCT_HERBIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_LABU, CardConstants.CARD_LABU_PATH, 500, 8, 5, Product.PRODUCT_HERBIVORE_FOOD));
        products.add(new Product(CardConstants.CARD_STROBERI, CardConstants.CARD_STROBERI_PATH, 350, 3, 4, Product.PRODUCT_HERBIVORE_FOOD));
        return products;
    }

    private void initializeStore(GridPane tokoPane, List<Product> products) {
        int productIndex = currentPage * ITEMS_PER_PAGE;
        int displayedItems = 0;

        DataPasser dataPasser = DataPasser.getInstance();

        Player currentPlayer = (dataPasser.currentPlayer == 1) ? dataPasser.player1 : dataPasser.player2;
        System.out.println(currentPlayer.getGuldenCount());

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
                            dataPasser.imageTemp = new Image(getClass().getResource(product.getCardPath()).toExternalForm());
                            dataPasser.labelTemp = product.getCardName();
                            dataPasser.productPrice = product.getSellPrice();
                            dataPasser.productStoreQuantity = StorePageController.getStore().getProductCount(product.getCardName());
                            dataPasser.productTemp = product;
                            System.out.println(dataPasser.productStoreQuantity);

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
                            }finally{
                                Stage stages = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                                stages.close();
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

    private void resetPane(Pane pane) {
        VBox imagePane = (VBox) pane.lookup("#imagePane");
        VBox detailsPane = (VBox) pane.lookup("#detailsPane");

        ImageView imageView = (ImageView) imagePane.lookup("#image");
        Label productLabel = (Label) imagePane.lookup("#labelNama");
        Label priceLabel = (Label) detailsPane.lookup("#labelHarga");
        Label quantityLabel = (Label) detailsPane.lookup("#labelJumlah");

        if (imageView != null) {
            imageView.setImage(null);
        }
        if (productLabel != null) {
            productLabel.setText("");
        }
        if (priceLabel != null) {
            priceLabel.setText("");
        }
        if (quantityLabel != null) {
            quantityLabel.setText("");
        }

        pane.setEffect(null);
        pane.setDisable(false);
        pane.setVisible(true);
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
        store.addProduct(product, jumlah);
    }

    public static void loadDataProductToStore(Product product, int jumlah) {
        store.addProduct(product, jumlah);
    }

    public static void resetAllDataStore() {
        store.setZeroCounts();
    }

    public static Store getStore() {
        return store;
    }
}
