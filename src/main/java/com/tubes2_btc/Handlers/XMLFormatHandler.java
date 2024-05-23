package com.tubes2_btc.Handlers;

import com.tubes2_btc.Interfaces.FormatHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

public class XMLFormatHandler implements FormatHandler {

    public XMLFormatHandler() {}

    @Override
    public String getFormatExtension() {
        return "XML";
    }
}
