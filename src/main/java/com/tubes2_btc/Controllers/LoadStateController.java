package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.*;
import com.tubes2_btc.Interfaces.FormatHandler;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.Flow;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import static com.tubes2_btc.Classes.CardConstants.CARD_BERUANG_INDEX;

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
    private AnchorPane formatAnchorPane;

    @FXML
    private Button browseButton;

    private Player player1;
    private Player player2;
    private String format;
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
        format = ".txt";
        formatTitledPane.setExpanded(false);
    }

    @FXML
    public void handleJsonButton() {
        formatTitledPane.setText("JSON");
        format = ".json";
        formatTitledPane.setExpanded(false);
    }

    @FXML
    public void handleBrowseButton() {
        directoryChooser = new DirectoryChooser();

        selectedDirectory = directoryChooser.showDialog(browseButton.getScene().getWindow());
        if (selectedDirectory != null) {
            System.out.println("Folder yang dipilih: " + ((File) selectedDirectory).getAbsolutePath());
        } else {
            System.out.println("Tidak ada folder yang dipilih.");
        }
    }

    public void handleLoadButton() {
        if (selectedDirectory != null) {
            System.out.println("Folder yang dipilih: " + selectedDirectory.getAbsolutePath());

            File[] files = selectedDirectory.listFiles();
            if (files != null) {
                System.out.println("Daftar file dalam folder:");
                for (File file : files) {
                    if (file.isFile()) {  
                        if (file.getName().endsWith(format)) {
                            readAndPrintFile(file);
                        }
                    }
                }
                if (mainPageController != null) {
                    mainPageController.loadGameState(player1, player2, turn, store);
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
        if (file.getName().equals("gamestate" + format)) {
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

        if(file.getName().equals("player1" + format)) {
            player1 = loadPlayer(file);
        }

        if(file.getName().equals("player2" + format)) {
            player2 = loadPlayer(file);
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

    private void addFormatButton(FormatHandler handler) {
        Button newButton = new Button(handler.getFormatExtension());
        newButton.setLayoutY(txtButton.getLayoutY() + txtButton.getPrefHeight() + 2);
        newButton.setPrefHeight(26);
        newButton.setPrefWidth(463);
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                formatTitledPane.setText(handler.getFormatExtension());
                format = "." + handler.getFormatExtension().toLowerCase();
                formatTitledPane.setExpanded(false);
            }
        });
        formatAnchorPane.getChildren().add(newButton);
    }

    public void loadFormatHandlerFromJar(File jarFile) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        try (JarInputStream jarInputStream = new JarInputStream(new FileInputStream(jarFile))) {
            JarEntry jarEntry;
            URL[] urls = { new URL("jar:file:" + jarFile.getAbsolutePath() + "!/") };
            URLClassLoader classLoader = new URLClassLoader(urls);

            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName().replace('/', '.').replace(".class", "");
                    try {
                        System.out.println("nama kelasnya " + className);
                        Class<?> loadedClass = classLoader.loadClass(className);
                        if (FormatHandler.class.isAssignableFrom(loadedClass)) {
                            try {
                                Constructor<?> constructor = loadedClass.getDeclaredConstructor();
                                System.out.println("Constructor found: " + constructor);
                                FormatHandler handler = (FormatHandler) constructor.newInstance();
                                addFormatButton(handler);
                            } catch (NoSuchMethodException e) {
                                System.out.println("Class " + className + " does not have a default constructor.");
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        System.out.println("Class " + className + " not found.");
                    }
                }
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        formatTitledPane.setText("TXT");
        format = ".txt";
        DataPasser dataPasser = DataPasser.getInstance();
//        dataPasser.loadState = this;
        if (dataPasser.jarFile != null) {
            loadFormatHandlerFromJar(dataPasser.jarFile);
        }
    }

    public Player loadPlayer(File file){
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int gulden = Integer.parseInt(reader.readLine().trim());
            int jumlahDeck = Integer.parseInt(reader.readLine().trim());

            List<Card> deck = new ArrayList<>();
            Random random = new Random();
            for (int i = 0; i < jumlahDeck; i++) {
                int rand = random.nextInt(1, 25);
                while (rand == CARD_BERUANG_INDEX){
                    rand = random.nextInt(1, 25);
                }
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
                    int idx;
                    
                    switch (lokasi.charAt(0)) {
                        case 'A': idx = 0; break;
                        case 'B': idx = 1; break;
                        case 'C': idx = 2; break;
                        case 'D': idx = 3; break;
                        case 'E': idx = 4; break;
                        case 'F': idx = 5; break;
                        default: idx = 0; break; 
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

                String numberPart = lokasi.substring(1);
                int idx = (Integer.parseInt(numberPart) - 1) * 5;

                switch (lokasi.charAt(0)) {
                    case 'A': idx = idx + 0; break;
                    case 'B': idx = idx + 1; break;
                    case 'C': idx = idx + 2; break;
                    case 'D': idx = idx + 3; break;
                    case 'E': idx = idx + 4; break;
                    default: idx = idx + 0; break;
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
                } else if (Card.createCard(storeMap.get(cardNameDeck)).getClass().getSimpleName().equals("Animal")){
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
                else {
                    Card randomCard = Card.createCard(storeMap.get(cardNameDeck));
                    farm.put(idx, randomCard);
                }

            }
            return new Player(gulden, deck, activeDeck, farm);
        } catch (IOException e) {
            System.out.println("Error saat membaca file: " + e.getMessage());
            return new Player();
        }
    }
}
