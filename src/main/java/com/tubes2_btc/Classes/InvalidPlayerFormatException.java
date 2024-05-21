package com.tubes2_btc.Classes;

import java.lang.Exception;

public class InvalidPlayerFormatException extends Exception {
    public InvalidPlayerFormatException(String message){
        super(message);
    }
}
