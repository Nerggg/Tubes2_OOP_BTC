package com.tubes2_btc.Classes;

public class Product extends Card {
    public static final String PRODUCT_CARNIVORE_FOOD = "Carnivore Food";
    public static final String PRODUCT_HERBIVORE_FOOD = "Herbivore Food";
    
    private int sellPrice;
    private int addedWeight;
    private int harvestAge;
    private String foodType;

    public Product(String cardName, String cardPath, int sellPrice, int addedWeight, int harvestAge, String foodType) {
        super(cardName, cardPath);
        this.sellPrice = sellPrice;
        this.addedWeight = addedWeight;
        this.harvestAge = harvestAge;
        this.foodType = foodType;
    }

    public int getSellPrice() {
        return this.sellPrice;
    }

    public int getAddedWeight(){
        return this.addedWeight;
    }

    public int getHarvestAge(){
        return this.harvestAge;
    }
}
