package com.tubes2_btc.Handlers;

import com.tubes2_btc.Interfaces.FormatHandler;

public class JSONFormatHandler implements FormatHandler {

    public JSONFormatHandler() {}

    @Override
    public String getFormatExtension() {
        return "JSON";
    }
}
