package com.tubes2_btc.Classes;

import java.lang.Exception;

public class InvalidGameStateFormatException extends Exception {
    public InvalidGameStateFormatException(String message) {
        super(message);
    }
}
