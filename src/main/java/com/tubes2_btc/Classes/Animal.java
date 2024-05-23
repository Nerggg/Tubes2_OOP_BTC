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
        this.isAccelerated = true;
    }

    public void delay() {
        this.weight += -5;
        if (this.weight < 0) {
            this.weight = 0;
        }
        this.isDelayed = true;
    }

    public Card harvest() {
        return createCard(resultProduct);
    }

    public boolean feed(Product product) {
        if (this.animalType == Animal.ANIMAL_OMNIVORA) {
            this.weight += product.getAddedWeight();
            return true;
        } else if (this.animalType == Animal.ANIMAL_KARNIVORA && product.getFoodType() == Product.PRODUCT_CARNIVORE_FOOD) {
            this.weight += product.getAddedWeight();
            return true;
        } else if (this.animalType == Animal.ANIMAL_HERBIVORA && product.getFoodType() == Product.PRODUCT_HERBIVORE_FOOD) {
            this.weight += product.getAddedWeight();
            return true;
        } else {
            return false;
        }
    }
}