package com.tubes2_btc.Classes;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.util.Random;

public class Player {
    // Static attributes
    private static int PLAYER_FARM_SLOTS = 20;
    private static int PLAYER_ACTIVE_DECK_SLOTS = 6;
    
    // Instance attributes
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
        InitializeDeck();
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

    public void InitializeDeck() {
        Random random = new Random();
        
        for (int i = 0; i < 6; i++) {
            int rand = random.nextInt(50);
            
            if (rand < 25) rand = 0;
            else rand -= 24;

            Card randomCard = Card.createCard(rand);
            activeDeck.put(i , randomCard);
        }
    }

    public Map<Integer, Card> getFarm() {
        return farm;
    }

    public Map<Integer, Card> getActiveDeck() {
        return activeDeck;
    }

    public void swapSlots(int index1, int index2, Map<Integer, Card> cont1, Map<Integer, Card> cont2) {
        Card temp = cont1.get(index1);

        cont1.put(index1, cont2.get(index2));
        cont2.put(index2, temp);

        System.out.println("Index " + index1 + ": " + cont1.get(index1).getCardName());
        System.out.println("Index " + index2 + ": " + cont2.get(index2).getCardName());
    }

    public int getFarmFreeSlots() {
        return PLAYER_FARM_SLOTS - farm.size();
    }

    public int getActiveDeckFreeSlots() {
        return PLAYER_ACTIVE_DECK_SLOTS - activeDeck.size();
    }
}
