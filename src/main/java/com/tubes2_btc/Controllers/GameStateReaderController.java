package com.tubes2_btc.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GameStateReaderController {
    @FXML
    private Button uploadButton;

    @FXML
    private TextArea textArea;

    @FXML
    private void handleUploadButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        Stage stage = (Stage) uploadButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                String content = Files.readString(Path.of(selectedFile.toURI()));
                String[] lines = content.split("\\R");
                textArea.setText(content);

                for (String line : lines){
                    System.out.println(line);
                }

                if (lines.length > 1){
                    int currentTurn = Integer.parseInt(lines[0]);
                    int jumlahItemDiShop = Integer.parseInt(lines[1]);

                    List<String> itemShop = new ArrayList<>(jumlahItemDiShop);

                    for (int i = 0; i < jumlahItemDiShop; i++){
                        itemShop.add(lines[2+i]);
                    }

                    System.out.println("CURRENT TURN: " + currentTurn);
                    System.out.println("JUMLAH ITEM DI SHOP: " + jumlahItemDiShop);
                    System.out.println("ITEM SHOP: " + itemShop);

                }


            } catch (IOException e) {
                textArea.setText("Failed to load the file.");
            }
        }
    }
}
