package com.tubes2_btc.Classes;

public class Animal extends Card {
    public static final String ANIMAL_KARNIVORA = "Karnivora";
    public static final String ANIMAL_HERBIVORA = "Herbivora";
    public static final String ANIMAL_OMNIVORA = "Omnivora";
    
    private int weight;
    private int harvestWeight;
    private String animalType;

    private int resultProduct;

    public Animal(String cardName, String cardPath, int weight, int harvestWeight, String animalType, int resultProduct) {
        super(cardName, cardPath);
        this.weight = weight;
        this.harvestWeight = harvestWeight;
        this.animalType = animalType;
        this.resultProduct = resultProduct;
    }

    public int getWeight() {
        return this.weight;
    }
    public int getHarvestWeight() {
        return this.harvestWeight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public String getAnimalType() {
        return this.animalType;
    }

    public int getWeightOrAge() {
        return this.weight;
    }

    public void setWeightOrAge(int weightOrAge) {
        this.weight = weightOrAge;
    }
    
    public void accelerate() {
        this.weight += 8;
    }

    public void delay() {
        this.weight += -5;
        if (this.weight < 0) {
            this.weight = 0;
        }
    }

    public Card harvest() {
        return createCard(resultProduct);
    }
}