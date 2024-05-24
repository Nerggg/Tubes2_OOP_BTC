package com.tubes2_btc.Classes;

public class Plant extends Card {
    private int age;
    private int harvestAge;

    private int resultProduct;

    public Plant(String cardName, String cardPath, int age, int harvestAge, int resultProduct) {
        super(cardName, cardPath);
        this.age = age;
        this.harvestAge = harvestAge;
        this.resultProduct = resultProduct;
    }

    public int getAge() {
        return this.age;
    }
    public int getHarvestAge() {
        return this.harvestAge;
    }

    public void setAge(int age) {
        this.age=age;
    }

    public void incrementAge() {
        this.age++;
    }

    public int getWeightOrAge() {
        return this.age;
    }

    public void setWeightOrAge(int weightOrAge) {
        this.age = weightOrAge;
    }

    public void accelerate() {
        this.age += 2;
        this.isAccelerated = true;
    }

    public void delay() {
        this.age += -2;
        if (this.age < 0) {
            this.age = 0;
        }
        this.isDelayed = true;
    }

    public Card harvest() {
        return createCard(resultProduct);
    }
}
