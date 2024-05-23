package com.tubes2_btc.Classes;

public class Card extends CardConstants {
    private String CardName;
    private String CardPath;

    // General flags
    private boolean isEmpty;

    // Item flags
    protected boolean isAccelerated;
    protected boolean isDelayed;
    protected boolean isProtected;
    protected boolean isTrapped;

    public Card(String cardName, String cardPath) {
        this.CardName = cardName;
        this.CardPath = cardPath;

        this.isEmpty = (cardName == CARD_EMPTY) ? true : false;
        this.isAccelerated = false;
        this.isDelayed = false;
        this.isProtected = false;
        this.isTrapped = false;
    }

    public  boolean IsEmpty(){
        return this.isEmpty;
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

    public int getWeightOrAge() {
        return 69;
    }

    public void setWeightOrAge(int x) {
        // Do nothing
    }

    public void accelerate() {
        // Do nothing   
    }

    public void delay() {
        // Do nothing
    }

    public void protect() {
        this.isProtected = true;
    }

    public void trap() {
        this.isTrapped = true;
    }

    public boolean isProtected() {
        return this.isProtected;
    }

    public boolean isTrapped() {
        return this.isTrapped;
    }

    public Card harvest() {
        return new Card(CARD_EMPTY, "");
    }
    public boolean isEmpty(){
        return this.isEmpty;
    }
}