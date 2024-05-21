package com.tubes2_btc.Classes;

public class Animal extends Card {
    public static final String ANIMAL_KARNIVORA = "Karnivora";
    public static final String ANIMAL_HERBIVORA = "Herbivora";
    public static final String ANIMAL_OMNIVORA = "Omnivora";
    
    private int weight;
    private int harvestWeight;
    private String animalType;

    private String resultProduct;

    public Animal(String cardName, String cardPath, int weight, int harvestWeight, String animalType, String resultProduct) {
        super(cardName, cardPath);
        this.weight = weight;
        this.harvestWeight = harvestWeight;
        this.animalType = animalType;
        this.resultProduct = resultProduct;
    }
}