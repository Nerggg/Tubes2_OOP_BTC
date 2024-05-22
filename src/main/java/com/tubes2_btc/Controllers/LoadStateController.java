package com.tubes2_btc.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class LoadStateController {

    @FXML
    private Button kembali;

    @FXML
    private TitledPane formatTitledPane;

    @FXML
    private Button txtButton;

    @FXML
    private Button jsonButton;

    @FXML
    private Button browseButton;

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
        FileChooser fileChooser = new FileChooser();
        String format = formatTitledPane.getText();
        FileChooser.ExtensionFilter extFilter;

        if ("TXT".equals(format)) {
            extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        } else if ("JSON".equals(format)) {
            extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        } else {
            extFilter = new FileChooser.ExtensionFilter("All files (*.*)", "*.*");
        }

        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = (Stage) browseButton.getScene().getWindow();
        fileChooser.showOpenDialog(stage);
    }

}