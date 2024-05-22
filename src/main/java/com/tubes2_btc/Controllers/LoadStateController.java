package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.Card;
import com.tubes2_btc.Classes.CardConstants;
import com.tubes2_btc.Classes.Product;
import com.tubes2_btc.Classes.Store;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadStateController {

    @FXML
    private Button kembali;

    @FXML
    private TitledPane formatTitledPane;

    @FXML
    private Button txtButton;

    @FXML
    private Button loadButton;


    @FXML
    private Button jsonButton;

    @FXML
    private Button browseButton;

    private int turn;
    private Store store;
    private DirectoryChooser directoryChooser;
    File selectedDirectory;
    private static Map<String, Integer> storeMap = new HashMap<>();

    @FXML
    private void handleClicked(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleTxtButton() {
        formatTitledPane.setText("TXT");
        formatTitledPane.setExpanded(false);
    }

    @FXML
    public void handleJsonButton() {
        formatTitledPane.setText("JSON");
        formatTitledPane.setExpanded(false);
    }

    @FXML
//    public void handleBrowseButton() {
//        FileChooser fileChooser = new FileChooser();
//        String format = formatTitledPane.getText();
//        FileChooser.ExtensionFilter extFilter;
//
//        if ("TXT".equals(format)) {
//            extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//        } else if ("JSON".equals(format)) {
//            extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
//        } else {
//            extFilter = new FileChooser.ExtensionFilter("All files (*.*)", "*.*");
//        }
//
//        fileChooser.getExtensionFilters().add(extFilter);
//        Stage stage = (Stage) browseButton.getScene().getWindow();
//        fileChooser.showOpenDialog(stage);
//    }
    public void handleBrowseButton() {
        directoryChooser = new DirectoryChooser();

        selectedDirectory = directoryChooser.showDialog(browseButton.getScene().getWindow());
        if (selectedDirectory != null) {
            System.out.println("Folder yang dipilih: " + ((File) selectedDirectory).getAbsolutePath());
            // Lakukan operasi lain yang diperlukan dengan folder yang dipilih
        } else {
            System.out.println("Tidak ada folder yang dipilih.");
        }
    }

    public void handleLoadButton() {

        if (selectedDirectory != null) {
            System.out.println("Folder yang dipilih: " + selectedDirectory.getAbsolutePath());

            String format = formatTitledPane.getText();

            File[] files = selectedDirectory.listFiles();
            if (files != null) {
                System.out.println("Daftar file dalam folder:");
                for (File file : files) {
                    if (file.isFile()) {  // Memastikan file (bukan direktori)
                        if (("TXT".equals(format) && file.getName().endsWith(".txt")) ||
                                ("JSON".equals(format) && file.getName().endsWith(".json"))) {
                            System.out.println(file.getName());
                            System.out.println("Isi File:");
                            readAndPrintFile(file);
                        }
                    }
                }
            } else {
                System.out.println("Folder kosong atau tidak dapat diakses.");
            }
        } else {
            System.out.println("Tidak ada folder yang dipilih.");
        }
    }

    private void readAndPrintFile(File file) {
        createMap();
        if(file.getName().equals("gamestate.txt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                turn = Integer.parseInt(reader.readLine());
                int numOfItems = Integer.parseInt(reader.readLine().trim());
                List<Product> products = new ArrayList<>(numOfItems);
                store = new Store(products);
                CardConstants cc = new CardConstants();
                for (int i = 0; i < numOfItems; i++) {
                    String line = reader.readLine();
                    String[] parts = line.split("\\s+");
                    if (parts.length == 2) {
                        String productName = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        Product temp = (Product) cc.createCard(storeMap.get(productName));
                        store.addProduct(temp,quantity);
                    } else {
                        System.out.println("Format data tidak sesuai pada baris " + (i + 2) + ": " + line);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error saat membaca file: " + e.getMessage());
            }
        }

        if(file.getName().equals("player1.txt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int gulden = Integer.parseInt(reader.readLine().trim());
                int deck = Integer.parseInt(reader.readLine().trim());
                int jumlahDeckAktif = Integer.parseInt(reader.readLine().trim());
                List<String> listDeckAktif = new ArrayList<>(jumlahDeckAktif);
                for (int i = 0; i < jumlahDeckAktif; i++) {
                    String temp = reader.readLine();
                    listDeckAktif.add(temp);
                }
                int jumlahDeckDiLadang = Integer.parseInt(reader.readLine().trim());
                List<String> listDeckLadang = new ArrayList<>(jumlahDeckDiLadang);
                for (int i = 0; i < jumlahDeckDiLadang; i++) {
                    String temp = reader.readLine();
                    listDeckLadang.add(temp);
                }
                System.out.println(listDeckAktif);
                System.out.println(listDeckLadang);
            } catch (IOException e) {
                System.out.println("Error saat membaca file: " + e.getMessage());
            }
        }

        if(file.getName().equals("player2.txt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int gulden = Integer.parseInt(reader.readLine().trim());
                int deck = Integer.parseInt(reader.readLine().trim());
                int jumlahDeckAktif = Integer.parseInt(reader.readLine().trim());
                List<String> listDeckAktif = new ArrayList<>(jumlahDeckAktif);
                for (int i = 0; i < jumlahDeckAktif; i++) {
                    String temp = reader.readLine();
                    listDeckAktif.add(temp);
                }
                int jumlahDeckDiLadang = Integer.parseInt(reader.readLine().trim());
                List<String> listDeckLadang = new ArrayList<>(jumlahDeckDiLadang);
                for (int i = 0; i < jumlahDeckDiLadang; i++) {
                    String temp = reader.readLine();
                    listDeckLadang.add(temp);
                }
                System.out.println(listDeckAktif);
                System.out.println(listDeckLadang);
            } catch (IOException e) {
                System.out.println("Error saat membaca file: " + e.getMessage());
            }
        }
        printStoreInfo(store);
    }

    public Map<String, Integer> createMap() {
        storeMap.put("HIU", 1);
        storeMap.put("SAPI", 2);
        storeMap.put("DOMBA", 3);
        storeMap.put("KUDA",4);
        storeMap.put("AYAM", 5);
        storeMap.put("BERUANG", 6);
        storeMap.put("LABU", 7);
        storeMap.put("JAGUNG", 8);
        storeMap.put("STROBERI", 9);
        storeMap.put("SUSU", 10);
        storeMap.put("TELUR", 11);
        storeMap.put("SIRIP_HIU", 12);
        storeMap.put("DAGING_KUDA", 13);
        storeMap.put("DAGING_DOMBA", 14);
        storeMap.put("DAGING_BERUANG", 15);
        storeMap.put("BIJI_LABU", 16);
        storeMap.put("BIJI_JAGUNG", 17);
        storeMap.put("BIJI_STROBERI", 18);
        storeMap.put("ACCELERATE", 19);
        storeMap.put("DELAY", 20);
        storeMap.put("INSTANT_HARVEST", 21);
        storeMap.put("DESTROY", 22);
        storeMap.put("PROTECT", 23);
        storeMap.put("TRAP", 24);

        return storeMap;
    }

    public static void printStoreInfo(Store store) {
        System.out.println("Daftar Produk di Toko:");
        for (Product product : store.getProducts()) {
            System.out.println("Nama Produk: " + product.getCardName() + ", Jumlah: " + store.getProductCount(product.getCardName()));
        }
    }

}