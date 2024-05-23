package com.tubes2_btc.Controllers;

import com.tubes2_btc.Classes.DataPasser;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadPluginController {

    @FXML
    private Button kembali;

    @FXML
    private Button choose;

    @FXML
    private Label success;

    @FXML
    private Label fileName;

    @FXML
    private Button upload;

    private File jarFile;

    @FXML
    private void handleClickedKembali(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleClickedChoose(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JAR files (*.jar)", "*.jar"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File jarFile = fileChooser.showOpenDialog(stage);

        if (jarFile != null) {
            fileName.setText(jarFile.getName());
            this.jarFile = jarFile;
        }
    }

    @FXML
    private void handleClickedUpload(ActionEvent event) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        DataPasser dataPasser = DataPasser.getInstance();
        success.setText("Upload plugin berhasil!");
        dataPasser.jarFile = this.jarFile;
    }
}