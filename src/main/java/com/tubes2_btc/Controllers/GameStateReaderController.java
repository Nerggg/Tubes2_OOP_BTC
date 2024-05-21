package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.InvalidGameStateFormatException;
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
    private void handleGameStateButtonAction() {
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

                // Validasi format file
                validateGameStateFormat(lines);

                int currentTurn = Integer.parseInt(lines[0]);
                int jumlahItemDiShop = Integer.parseInt(lines[1]);

                List<String> itemShop = new ArrayList<>(jumlahItemDiShop);
                for (int i = 0; i < jumlahItemDiShop; i++) {
                    itemShop.add(lines[2 + i]);
                }

                System.out.println("CURRENT TURN: " + currentTurn);
                System.out.println("JUMLAH ITEM DI SHOP: " + jumlahItemDiShop);
                System.out.println("ITEM SHOP: " + itemShop);

            } catch (IOException e) {
                textArea.setText("Failed to load the file.");
            } catch (InvalidGameStateFormatException e) {
                textArea.setText(e.getMessage());
                System.out.println(e.getMessage());
            } catch (NumberFormatException e) {
                textArea.setText("Failed to parse number in the game state.");
                System.out.println(e.getMessage());
            }
        }
    }

    private void validateGameStateFormat(String[] lines) throws InvalidGameStateFormatException {
        if (lines.length < 2) {
            throw new InvalidGameStateFormatException("Invalid file format: The file should contain at least 2 lines.");
        }

        try {
            Integer.parseInt(lines[0]);
            int jumlahItemDiShop = Integer.parseInt(lines[1]);

            if (lines.length < 2 + jumlahItemDiShop) {
                throw new InvalidGameStateFormatException("Invalid file format: Number of items in the shop does not match the specified amount.");
            }
        } catch (NumberFormatException e) {
            throw new InvalidGameStateFormatException("Invalid file format: The first two lines should be integers.");
        }
    }
}
