package com.tubes2_btc.Classes;

public class Card extends CardConstants {
    private String CardName;
    private String CardPath;

    // General flags
    private boolean isEmpty;

    // Item flags
    private boolean isAccelerated;
    private boolean isDelayed;
    private boolean isProtected;
    private boolean isTrapped;

    public Card(String cardName, String cardPath) {
        this.CardName = cardName;
        this.CardPath = cardPath;

        this.isEmpty = (cardName == CARD_EMPTY) ? true : false;
        this.isAccelerated = false;
        this.isDelayed = false;
        this.isProtected = false;
        this.isTrapped = false;
    }

    public String getCardName() {
        return this.CardName;
    }

    public String getCardPath() {
        return this.CardPath;
    }

    public String getCardActive() {
        String temp = "";
        if (this.isAccelerated){
            temp = temp + "Accelerated ";
        }
        if(this.isDelayed){
            temp = temp + "Delayed ";
        }
        if(this.isProtected){
            temp = temp + "Protected ";
        }
        if(this.isTrapped){
            temp = temp + "Trapped ";
        }
        return temp;
    }

    public String getCardActive2() {
        String temp = "";
        if (this.isAccelerated){
            temp = temp + "ACCELERATE ";
        }
        if(this.isDelayed){
            temp = temp + "DELAY ";
        }
        if(this.isProtected){
            temp = temp + "PROTECT ";
        }
        if(this.isTrapped){
            temp = temp + "TRAP ";
        }
        return temp;
    }

    public int getCountCardActive() {
        int count=0;
        if (this.isAccelerated){
            count++;
        }
        if(this.isDelayed){
            count++;
        }
        if(this.isProtected){
            count++;
        }
        if(this.isTrapped){
            count++;
        }
        return count;
    }

    public void setAccelerated(){
        this.isAccelerated = true;
    }

    public void setDelayed(){
        this.isDelayed = true;
    }

    public void setProtected(){
        this.isProtected = true;
    }

    public void setTrapped(){
        this.isTrapped = true;
    }

    public boolean isEmpty(){
        return this.isEmpty;
    }
}