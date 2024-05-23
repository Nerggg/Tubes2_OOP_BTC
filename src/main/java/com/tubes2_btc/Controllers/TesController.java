//import com.tubes2_btc.Classes.DataPasser;
//import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.scene.input.MouseEvent;
//import javafx.stage.Modality;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//
//private void initializeStore(GridPane tokoPane, List<Product> products) {
//    int productIndex = currentPage * ITEMS_PER_PAGE;
//    int displayedItems = 0;
//
//    for (Node node : tokoPane.getChildren()) {
//        if (node instanceof Pane) {
//            Pane outerPane = (Pane) node;
//            if (displayedItems < ITEMS_PER_PAGE && productIndex < products.size()) {
//                Product product = products.get(productIndex);
//
//                VBox imagePane = (VBox) outerPane.lookup("#imagePane");
//                VBox detailsPane = (VBox) outerPane.lookup("#detailsPane");
//
//                ImageView imageView = (ImageView) imagePane.lookup("#image");
//                Label productLabel = (Label) imagePane.lookup("#labelNama");
//                Label priceLabel = (Label) detailsPane.lookup("#labelHarga");
//                Label quantityLabel = (Label) detailsPane.lookup("#labelJumlah");
//
//                if (imageView != null) {
//                    URL imageUrl = getClass().getResource(product.getCardPath());
//                    if (imageUrl != null) {
//                        Image imageNew = new Image(imageUrl.toExternalForm());
//                        imageView.setImage(imageNew);
//                    } else {
//                        System.err.println("Gambar tidak ditemukan: " + product.getCardPath());
//                    }
//                }
//
//                if (productLabel != null) {
//                    productLabel.setText(product.getCardName());
//                }
//                if (priceLabel != null) {
//                    priceLabel.setText("Gd. " + product.getSellPrice());
//                }
//
//                // Get product count from store
//                int productCount = store.getProductCount(product.getCardName());
//                if (quantityLabel != null) {
//                    quantityLabel.setText(String.valueOf(productCount));
//                }
//
//                // Check product quantity
//                if (productCount == 0) {
//                    ColorAdjust colorAdjust = new ColorAdjust();
//                    colorAdjust.setBrightness(-0.5);
//                    outerPane.setEffect(colorAdjust);
//                    outerPane.setDisable(true);
//                } else {
//                    outerPane.setEffect(null);
//                    outerPane.setDisable(false);
//                }
//
//                outerPane.setVisible(true);
//                outerPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent mouseEvent) {
//                        DataPasser dataPasser = DataPasser.getInstance();
//                        dataPasser.imageTemp = new Image(getClass().getResource(product.getCardPath()).toExternalForm());
//                        dataPasser.labelTemp = product.getCardName();
//
//                        try {
//                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tubes2_btc/Pages/store-popup.fxml"));
//                            Parent root = fxmlLoader.load();
//                            Stage stage = new Stage();
//                            stage.initModality(Modality.APPLICATION_MODAL);
//                            stage.initStyle(StageStyle.UNDECORATED);
//                            stage.setScene(new Scene(root));
//
//                            // Get the parent stage (assuming the button is within a stage)
//                            Stage parentStage = (Stage) ((Parent) mouseEvent.getSource()).getScene().getWindow();
//
//                            // Center the new stage in the parent stage
//                            stage.setOnShown(e -> {
//                                stage.setX(parentStage.getX() + (parentStage.getWidth() / 2) - (stage.getWidth() / 2));
//                                stage.setY(parentStage.getY() + (parentStage.getHeight() / 2) - (stage.getHeight() / 2));
//                            });
//
//                            stage.showAndWait();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//
//                productIndex++;
//                displayedItems++;
//            } else {
//                outerPane.setVisible(false);
//            }
//        }
//    }
//}
//
//private void handlePaneClicked(Pane pane) {
//    System.out.println("Pane clicked: " + pane);
//}
//
//@FXML
//private void handleClicked(ActionEvent event) {
//    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//    stage.close();
//}
//
//@FXML
//private void handleNext(ActionEvent event) {
//    if (currentPage < Math.ceil((double) store.getProducts().size() / ITEMS_PER_PAGE) - 1) {
//        currentPage++;
//        updatePaginationButtons();
//        initializeStore(Toko, store.getProducts());
//    }
//}
//
//@FXML
//private void handlePrevious(ActionEvent event) {
//    if (currentPage > 0) {
//        currentPage--;
//        updatePaginationButtons();
//        initializeStore(Toko, store.getProducts());
//    }
//}
//
//private void updatePaginationButtons() {
//    int totalItems = store.getProducts().size();
//    int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
//
//    previousButton.setDisable(currentPage <= 0);
//    nextButton.setDisable(currentPage >= totalPages - 1);
//}