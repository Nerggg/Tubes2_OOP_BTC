package com.tubes2_btc.Classes;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.util.Random;

public class Player {
    private int guldenCount;
    
    private List<Card> deck;
    private Map<Integer, Card> activeDeck;
    private Map<Integer, Card> farm;

    public Player() {
        this.guldenCount = 0;
        this.deck = new ArrayList<>();
        this.activeDeck = new HashMap<>();
        this.farm = new HashMap<>();

        initializeFarm();
    }

    public void initializeFarm() {
        Random random = new Random();
        
        for (int i = 0; i < 20; i++) {
            int rand = random.nextInt(50);
            
            if (rand < 25) rand = 0;
            else rand -= 24;

            Card randomCard = Card.createCard(rand);
            farm.put(i, randomCard);
        }
    }

    public Map<Integer, Card> getFarm() {
        return farm;
    }

    public void swapFarm(int index1, int index2) {
        Card temp = farm.get(index1);
        
        farm.put(index1, farm.get(index2));
        farm.put(index2, temp);

        System.out.println("Index " + index1 + ": " + farm.get(index1).getCardName());
        System.out.println("Index " + index2 + ": " + farm.get(index2).getCardName());
    }
}
