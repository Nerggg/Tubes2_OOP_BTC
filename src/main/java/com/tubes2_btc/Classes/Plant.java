package com.tubes2_btc.Classes;

public class Plant extends Card {
    private int age;
    private int harvestAge;

    private String resultProduct;

    public Plant(String cardName, String cardPath, int age, int harvestAge, String resultProduct) {
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
}
