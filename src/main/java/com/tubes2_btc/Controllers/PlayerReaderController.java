package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.InvalidPlayerFormatException;
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

public class PlayerReaderController {
    @FXML
    private Button uploadButton;

    @FXML
    private TextArea textArea;

    @FXML
    private void handlePlayerLoadButtonAction() {
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

                // Parsing langsung di sini
                try {
                    int index = 0;
                    int jumlahGulden = Integer.parseInt(lines[index++]);
                    int jumlahDeck = Integer.parseInt(lines[index++]);
                    int jumlahDeckAktif = Integer.parseInt(lines[index++]);

                    String[][] deckAktif = new String[jumlahDeckAktif][2];
                    for (int i = 0; i < jumlahDeckAktif; i++) {
                        String[] parts = lines[index++].split(" ");
                        if (parts.length != 2) {
                            throw new InvalidPlayerFormatException("Invalid deck active format at line " + (index - 1));
                        }
                        deckAktif[i] = parts;
                    }

                    int jumlahKartuLadang = Integer.parseInt(lines[index++]);
                    List<String[]> kartuLadang = new ArrayList<>();
                    for (int i = 0; i < jumlahKartuLadang; i++) {
                        String[] parts = lines[index++].split(" ");
                        if (parts.length < 4) {
                            throw new InvalidPlayerFormatException("Invalid ladang format at line " + (index - 1));
                        }
                        String[] ladangItem = new String[4 + Integer.parseInt(parts[3])];
                        System.arraycopy(parts, 0, ladangItem, 0, parts.length);
                        kartuLadang.add(ladangItem);
                    }

                    // Output hasil parsing untuk verifikasi
                    System.out.println("JUMLAH GULDEN: " + jumlahGulden);
                    System.out.println("JUMLAH DECK: " + jumlahDeck);
                    System.out.println("JUMLAH DECK AKTIF: " + jumlahDeckAktif);
                    for (String[] item : deckAktif) {
                        System.out.println(item[0] + " " + item[1]);
                    }
                    System.out.println("JUMLAH KARTU LADANG: " + jumlahKartuLadang);
                    for (String[] item : kartuLadang) {
                        System.out.print(item[0] + " " + item[1] + " " + item[2] + " " + item[3]);
                        for (int j = 4; j < item.length; j++) {
                            System.out.print(" " + item[j]);
                        }
                        System.out.println();
                    }

                } catch (InvalidPlayerFormatException | NumberFormatException e) {
                    textArea.setText(e.getMessage());
                    System.out.println(e.getMessage());
                }

            } catch (IOException e) {
                textArea.setText("Failed to load the file.");
            }
        }
    }
}
