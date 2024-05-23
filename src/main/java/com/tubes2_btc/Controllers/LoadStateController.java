package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.*;
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
import java.util.*;

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

    private Player player1;
    private Player player2;
    private int turn;
    private Store store;
    private DirectoryChooser directoryChooser;
    File selectedDirectory;
    private static Map<String, Integer> storeMap = new HashMap<>();

    // Main page controller
    private MainPageController mainPageController;

    public void setMainPageController(MainPageController mainPageController) {
        this.mainPageController = mainPageController;
    }

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
                    if (file.isFile()) { // Memastikan file (bukan direktori)
                        if (("TXT".equals(format) && file.getName().endsWith(".txt")) ||
                                ("JSON".equals(format) && file.getName().endsWith(".json"))) {
                            System.out.println(file.getName());
                            System.out.println("Isi File:");
                            readAndPrintFile(file);
                        }
                    }
                }
                if (mainPageController != null) {
                    mainPageController.loadGameState(player1, player2, turn);
                } else {
                    System.err.println("mainPageController is null");
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
        if (file.getName().equals("gamestate.txt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                turn = Integer.parseInt(reader.readLine());
                int numOfItems = Integer.parseInt(reader.readLine().trim());
                store = StorePageController.getStore();
                CardConstants cc = new CardConstants();
                StorePageController.resetAllDataStore();
                for (int i = 0; i < numOfItems; i++) {
                    String line = reader.readLine();
                    String[] parts = line.split("\\s+");
                    if (parts.length == 2) {
                        String productName = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        Product temp = (Product) cc.createCard(storeMap.get(productName));
                        StorePageController.loadDataProductToStore(temp, quantity); // Ubah fungsi ini
                    } else {
                        System.out.println("Format data tidak sesuai pada baris " + (i + 2) + ": " + line);
                    }
                }
                StorePageController.initializeStore(); // Tambahkan ini untuk memperbarui tampilan
            } catch (IOException e) {
                System.out.println("Error saat membaca file: " + e.getMessage());
            }
        }

        if (file.getName().equals("player1.txt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int gulden = Integer.parseInt(reader.readLine().trim());
                int jumlahDeck = Integer.parseInt(reader.readLine().trim());

                List<Card> deck = new ArrayList<>();
                Random random = new Random();
                for (int i = 0; i < jumlahDeck; i++) {
                    int rand = random.nextInt(50);

                    if (rand < 25)
                        rand = 0;
                    else
                        rand -= 24;

                    Card randomCard = Card.createCard(rand);
                    deck.add(randomCard);
                }

                Map<Integer, Card> activeDeck = new HashMap<>();
                for (int i = 0; i < 6; i++) {
                    activeDeck.put(i, Card.createCard(0));
                }
                int jumlahDeckAktif = Integer.parseInt(reader.readLine().trim());
                for (int i = 0; i < jumlahDeckAktif; i++) {
                    String line = reader.readLine();
                    String[] parts = line.split("\\s+");
                    if (parts.length == 2) {
                        String lokasi = parts[0];
                        String cardNameDeck = parts[1];
                        int idx = 0;
                        if (lokasi.charAt(0) == 'A') {
                            String numberPart = lokasi.substring(1);
                            idx = Integer.parseInt(numberPart);
                            idx--;
                        }
                        if (lokasi.charAt(0) == 'B') {
                            String numberPart = lokasi.substring(1);
                            idx = Integer.parseInt(numberPart);
                            idx = idx + 5;
                            idx--;
                        }
                        if (lokasi.charAt(0) == 'C') {
                            String numberPart = lokasi.substring(1);
                            idx = Integer.parseInt(numberPart);
                            idx = idx + 10;
                            idx--;
                        }
                        if (lokasi.charAt(0) == 'D') {
                            String numberPart = lokasi.substring(1);
                            idx = Integer.parseInt(numberPart);
                            idx = idx + 15;
                            idx--;
                        }
                        Card randomCard = Card.createCard(storeMap.get(cardNameDeck));
                        activeDeck.put(idx, randomCard);
                    }
                }

                Map<Integer, Card> farm = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    farm.put(i, Card.createCard(0));
                }
                int jumlahDeckDiLadang = Integer.parseInt(reader.readLine().trim());
                for (int i = 0; i < jumlahDeckDiLadang; i++) {
                    String line = reader.readLine();
                    String[] parts = line.split("\\s+");
                    String lokasi = parts[0];
                    String cardNameDeck = parts[1];
                    int idx = 0;
                    if (lokasi.charAt(0) == 'A') {
                        String numberPart = lokasi.substring(1);
                        idx = Integer.parseInt(numberPart);
                        idx--;
                    }
                    if (lokasi.charAt(0) == 'B') {
                        String numberPart = lokasi.substring(1);
                        idx = Integer.parseInt(numberPart);
                        idx = idx + 5;
                        idx--;
                    }
                    if (lokasi.charAt(0) == 'C') {
                        String numberPart = lokasi.substring(1);
                        idx = Integer.parseInt(numberPart);
                        idx = idx + 10;
                        idx--;
                    }
                    if (lokasi.charAt(0) == 'D') {
                        String numberPart = lokasi.substring(1);
                        idx = Integer.parseInt(numberPart);
                        idx = idx + 15;
                        idx--;
                    }

                    if (Card.createCard(storeMap.get(cardNameDeck)).getClass().getSimpleName().equals("Plant")) {
                        Plant randomCard = (Plant) Card.createCard(storeMap.get(cardNameDeck));
                        randomCard.setAge(Integer.parseInt(parts[2]));
                        int jumlahCardActive = Integer.parseInt(parts[3]);
                        for (int j = 1; j <= jumlahCardActive; j++) {
                            String cardActive = parts[3 + j];
                            if (cardActive.equals("ACCELERATE")) {
                                randomCard.setAccelerated();
                            }
                            if (cardActive.equals("DELAY")) {
                                randomCard.setDelayed();
                            }
                            if (cardActive.equals("PROTECT")) {
                                randomCard.setProtected();
                            }
                            if (cardActive.equals("TRAPPED")) {
                                randomCard.setTrapped();
                            }
                        }
                        farm.put(idx, randomCard);
                    } else {
                        Animal randomCard = (Animal) Card.createCard(storeMap.get(cardNameDeck));
                        randomCard.setWeight(Integer.parseInt(parts[2]));
                        int jumlahCardActive = Integer.parseInt(parts[3]);
                        for (int j = 1; j <= jumlahCardActive; j++) {
                            String cardActive = parts[3 + j];
                            if (cardActive.equals("ACCELERATE")) {
                                randomCard.setAccelerated();
                            }
                            if (cardActive.equals("DELAY")) {
                                randomCard.setDelayed();
                            }
                            if (cardActive.equals("PROTECT")) {
                                randomCard.setProtected();
                            }
                            if (cardActive.equals("TRAPPED")) {
                                randomCard.setTrapped();
                            }
                        }
                        farm.put(idx, randomCard);
                    }

                }
                player1 = new Player(gulden, deck, activeDeck, farm);
            } catch (IOException e) {
                System.out.println("Error saat membaca file: " + e.getMessage());
            }
        }

        if (file.getName().equals("player2.txt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                int gulden = Integer.parseInt(reader.readLine().trim());
                int jumlahDeck = Integer.parseInt(reader.readLine().trim());

                List<Card> deck = new ArrayList<>();
                Random random = new Random();
                for (int i = 0; i < jumlahDeck; i++) {
                    int rand = random.nextInt(50);

                    if (rand < 25)
                        rand = 0;
                    else
                        rand -= 24;

                    Card randomCard = Card.createCard(rand);
                    deck.add(randomCard);
                }

                Map<Integer, Card> activeDeck = new HashMap<>();
                for (int i = 0; i < 6; i++) {
                    activeDeck.put(i, Card.createCard(0));
                }
                int jumlahDeckAktif = Integer.parseInt(reader.readLine().trim());
                for (int i = 0; i < jumlahDeckAktif; i++) {
                    String line = reader.readLine();
                    String[] parts = line.split("\\s+");
                    if (parts.length == 2) {
                        String lokasi = parts[0];
                        String cardNameDeck = parts[1];
                        int idx = 0;
                        if (lokasi.charAt(0) == 'A') {
                            String numberPart = lokasi.substring(1);
                            idx = Integer.parseInt(numberPart);
                            idx--;
                        }
                        if (lokasi.charAt(0) == 'B') {
                            String numberPart = lokasi.substring(1);
                            idx = Integer.parseInt(numberPart);
                            idx = idx + 5;
                            idx--;
                        }
                        if (lokasi.charAt(0) == 'C') {
                            String numberPart = lokasi.substring(1);
                            idx = Integer.parseInt(numberPart);
                            idx = idx + 10;
                            idx--;
                        }
                        if (lokasi.charAt(0) == 'D') {
                            String numberPart = lokasi.substring(1);
                            idx = Integer.parseInt(numberPart);
                            idx = idx + 15;
                            idx--;
                        }
                        Card randomCard = Card.createCard(storeMap.get(cardNameDeck));
                        activeDeck.put(idx, randomCard);
                    }
                }

                Map<Integer, Card> farm = new HashMap<>();
                for (int i = 0; i < 20; i++) {
                    farm.put(i, Card.createCard(0));
                }
                int jumlahDeckDiLadang = Integer.parseInt(reader.readLine().trim());
                for (int i = 0; i < jumlahDeckDiLadang; i++) {
                    String line = reader.readLine();
                    String[] parts = line.split("\\s+");
                    String lokasi = parts[0];
                    String cardNameDeck = parts[1];
                    int idx = 0;
                    if (lokasi.charAt(0) == 'A') {
                        String numberPart = lokasi.substring(1);
                        idx = Integer.parseInt(numberPart);
                        idx--;
                    }
                    if (lokasi.charAt(0) == 'B') {
                        String numberPart = lokasi.substring(1);
                        idx = Integer.parseInt(numberPart);
                        idx = idx + 5;
                        idx--;
                    }
                    if (lokasi.charAt(0) == 'C') {
                        String numberPart = lokasi.substring(1);
                        idx = Integer.parseInt(numberPart);
                        idx = idx + 10;
                        idx--;
                    }
                    if (lokasi.charAt(0) == 'D') {
                        String numberPart = lokasi.substring(1);
                        idx = Integer.parseInt(numberPart);
                        idx = idx + 15;
                        idx--;
                    }
                    if (Card.createCard(storeMap.get(cardNameDeck)).getClass().getSimpleName().equals("Plant")) {
                        Plant randomCard = (Plant) Card.createCard(storeMap.get(cardNameDeck));
                        randomCard.setAge(Integer.parseInt(parts[2]));
                        int jumlahCardActive = Integer.parseInt(parts[3]);
                        for (int j = 1; j <= jumlahCardActive; j++) {
                            String cardActive = parts[3 + j];
                            if (cardActive.equals("ACCELERATE")) {
                                randomCard.setAccelerated();
                            }
                            if (cardActive.equals("DELAY")) {
                                randomCard.setDelayed();
                            }
                            if (cardActive.equals("PROTECT")) {
                                randomCard.setProtected();
                            }
                            if (cardActive.equals("TRAPPED")) {
                                randomCard.setTrapped();
                            }
                        }
                        farm.put(idx, randomCard);
                    } else {
                        Animal randomCard = (Animal) Card.createCard(storeMap.get(cardNameDeck));
                        randomCard.setWeight(Integer.parseInt(parts[2]));
                        int jumlahCardActive = Integer.parseInt(parts[3]);
                        for (int j = 1; j <= jumlahCardActive; j++) {
                            String cardActive = parts[3 + j];
                            if (cardActive.equals("ACCELERATE")) {
                                randomCard.setAccelerated();
                            }
                            if (cardActive.equals("DELAY")) {
                                randomCard.setDelayed();
                            }
                            if (cardActive.equals("PROTECT")) {
                                randomCard.setProtected();
                            }
                            if (cardActive.equals("TRAPPED")) {
                                randomCard.setTrapped();
                            }
                        }
                        farm.put(idx, randomCard);
                    }

                }
                player2 = new Player(gulden, deck, activeDeck, farm);
            } catch (IOException e) {
                System.out.println("Error saat membaca file: " + e.getMessage());
            }
        }
    }

    public Map<String, Integer> createMap() {
        storeMap.put("HIU", 1);
        storeMap.put("SAPI", 2);
        storeMap.put("DOMBA", 3);
        storeMap.put("KUDA", 4);
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
            System.out.println("Nama Produk: " + product.getCardName() + ", Jumlah: "
                    + store.getProductCount(product.getCardName()));
        }
    }

}