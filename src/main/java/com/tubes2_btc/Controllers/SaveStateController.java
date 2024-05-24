package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.*;
import com.tubes2_btc.Interfaces.FormatHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class SaveStateController {

    @FXML
    private Button kembali;

    @FXML
    private Button txtButton;

    @FXML
    private Button jsonButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button browseButton;

    @FXML
    private TitledPane formatTitledPane;

    @FXML
    private AnchorPane formatAnchorPane;

    private static Map<String, String> cardMap = new HashMap<>();
    private String selectedFormat = "";
    private File selectedDirectory;

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
    private void handleTxtButton(ActionEvent event) {
        selectedFormat = "TXT";
        formatTitledPane.setText("TXT");
        formatTitledPane.setExpanded(false);
    }

    @FXML
    private void handleJsonButton(ActionEvent event) {
        selectedFormat = "JSON";
        formatTitledPane.setText("JSON");
        formatTitledPane.setExpanded(false);
    }

    @FXML
    private void handleBrowseButton(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");
        Stage stage = (Stage) browseButton.getScene().getWindow();
        selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            System.out.println("Selected folder: " + selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        if (selectedFormat.isEmpty()) {
            System.out.println("Please select a format.");
            return;
        }

        if (selectedDirectory == null) {
            System.out.println("Please select a folder.");
            return;
        }

        File filePlayer1 = new File(selectedDirectory, "player1" + selectedFormat.toLowerCase());
        File filePlayer2 = new File(selectedDirectory, "player2" + selectedFormat.toLowerCase());
        File fileGameState = new File(selectedDirectory, "gamestate" + selectedFormat.toLowerCase());

        cardMap = createMap();
        if (filePlayer1!=null){
            savePlayer(mainPageController.getPlayer1(), filePlayer1);
        }

        if (filePlayer2!=null){
            savePlayer(mainPageController.getPlayer2(), filePlayer2);
        }

        if (fileGameState != null) {
            try (FileWriter fileWriter = new FileWriter(fileGameState)) {
                fileWriter.write(Integer.toString(mainPageController.getCurrentTurn()));
                fileWriter.write("\n");
                Store store = mainPageController.getStore();
                List<Product> products = store.getProducts();
                int count = 0;
                for (Product product : products) {
                    if (store.getProductCount(product.getCardName())!=0){
                        count++;
                    }
                }
                fileWriter.write(Integer.toString(count));
                fileWriter.write("\n");
                for (Product product : products) {
                    if (store.getProductCount(product.getCardName())!=0){
                        fileWriter.write(cardMap.get(product.getCardName()));
                        fileWriter.write(" ");
                        fileWriter.write(Integer.toString(store.getProductCount(product.getCardName())));
                        fileWriter.write("\n");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, String> createMap() {
        cardMap.put("Hiu Darat", "HIU");
        cardMap.put("Sapi", "SAPI");
        cardMap.put("Domba", "DOMBA");
        cardMap.put("Kuda", "KUDA");
        cardMap.put("Ayam", "AYAM");
        cardMap.put("Beruang", "BERUANG");
        cardMap.put("Labu", "LABU");
        cardMap.put("Jagung", "JAGUNG");
        cardMap.put("Stroberi", "STROBERI");
        cardMap.put("Susu", "SUSU");
        cardMap.put("Telur", "TELUR");
        cardMap.put("Sirip Hiu", "SIRIP_HIU");
        cardMap.put("Daging Kuda", "DAGING_KUDA");
        cardMap.put("Daging Domba", "DAGING_DOMBA");
        cardMap.put("Daging Beruang", "DAGING_BERUANG");
        cardMap.put("Biji Labu", "BIJI_LABU");
        cardMap.put("Biji Jagung", "BIJI_JAGUNG");
        cardMap.put("Biji Stroberi", "BIJI_STROBERI");
        cardMap.put("Accelerate", "ACCELERATE");
        cardMap.put("Delay", "DELAY");
        cardMap.put("Instant Harvest", "INSTANT_HARVEST");
        cardMap.put("Destroy", "DESTROY");
        cardMap.put("Protect", "PROTECT");
        cardMap.put("Trap", "TRAP");

        return cardMap;
    }

    public void savePlayer(Player player, File file){
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(Integer.toString(player.getGuldenCount()));
            fileWriter.write("\n");
            fileWriter.write(Integer.toString(player.getDeckSize()));
            fileWriter.write("\n");
            Map<Integer, Card> activeDeck = player.getActiveDeck();
            int count=0;
            for (Map.Entry<Integer, Card> entry : activeDeck.entrySet()) {
                Card card = entry.getValue();
                if (card.isEmpty() == false) {
                    count++;
                }
            }
            fileWriter.write(Integer.toString(count));
            fileWriter.write("\n");
            for (Map.Entry<Integer, Card> entry : activeDeck.entrySet()) {
                Integer index = entry.getKey();
                Card card = entry.getValue();
                if (card.isEmpty() == false) {
                    switch (index % 5) {
                        case 0:
                            fileWriter.write('A');
                            break;
                        case 1:
                            fileWriter.write('B');
                            break;
                        case 2:
                            fileWriter.write('C');
                            break;
                        case 3:
                            fileWriter.write('D');
                            break;
                        case 4:
                            fileWriter.write('E');
                            break;
                        default:
                            fileWriter.write('A');
                            break;
                    }
                    fileWriter.write("0");
                    fileWriter.write(Integer.toString((index / 5) + 1));
                    fileWriter.write(" ");
                    fileWriter.write(cardMap.get(card.getCardName()));
                    fileWriter.write("\n");
                }
            }

            Map<Integer, Card> farm = player.getFarm();
            count=0;
            for (Map.Entry<Integer, Card> entry : farm.entrySet()) {
                Card card = entry.getValue();
                if (card.isEmpty() == false) {
                    count++;
                }
            }
            fileWriter.write(Integer.toString(count));
            fileWriter.write("\n");
            for (Map.Entry<Integer, Card> entry : farm.entrySet()) {
                Integer index = entry.getKey();
                Card card = entry.getValue();
                if (card.isEmpty() == false) {
                    switch (index % 5) {
                        case 0:
                            fileWriter.write('A');
                            break;
                        case 1:
                            fileWriter.write('B');
                            break;
                        case 2:
                            fileWriter.write('C');
                            break;
                        case 3:
                            fileWriter.write('D');
                            break;
                        case 4:
                            fileWriter.write('E');
                            break;
                        default:
                            fileWriter.write('A');
                            break;
                    }
                    fileWriter.write("0");
                    fileWriter.write(Integer.toString((index / 5) + 1));
                    fileWriter.write(" ");
                    fileWriter.write(cardMap.get(card.getCardName()));
                    fileWriter.write(" ");
                    if (card.getClass().getSimpleName().equals("Plant")) {
                        Plant randomCard = (Plant) card;
                        fileWriter.write(Integer.toString(randomCard.getAge()));
                    } else if (card.getClass().getSimpleName().equals("Animal")){
                        Animal randomCard = (Animal) card;
                        fileWriter.write(Integer.toString(randomCard.getWeight()));
                    } else {
                        fileWriter.write(Integer.toString(0));
                    }
                    fileWriter.write(" ");
                    fileWriter.write(Integer.toString(card.getCountCardActive()));
                    fileWriter.write(" ");
                    fileWriter.write(card.getCardActive2());
                    fileWriter.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                selectedFormat = "." + handler.getFormatExtension().toLowerCase();
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
        selectedFormat = ".txt";
        DataPasser dataPasser = DataPasser.getInstance();
//        dataPasser.loadState = this;
        if (dataPasser.jarFile != null) {
            loadFormatHandlerFromJar(dataPasser.jarFile);
        }
    }
}
