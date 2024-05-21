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

public class TxtController {
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
                textArea.setText(content);
            } catch (IOException e) {
                textArea.setText("Failed to load the file.");
            }
        }
    }
}
