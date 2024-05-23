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
        this.guldenCount = new Random().nextInt(1000);
        this.deck = new ArrayList<>();
        this.activeDeck = new HashMap<>();
        this.farm = new HashMap<>();

        initializeFarm();
        InitializeDeck();
    }

    public Player(int guldenCount, List<Card> deck, Map<Integer, Card> activeDeck, Map<Integer, Card> farm) {
        this.guldenCount = guldenCount;
        this.deck = deck;
        this.activeDeck = activeDeck;
        this.farm = farm;
    }

    public void copyFrom(Player player) {
        this.guldenCount = player.getGuldenCount();
        this.deck = player.deck;
        this.activeDeck = player.activeDeck;
        this.farm = player.farm;
    }

    public void initializeFarm() {
        Random random = new Random();
        
        for (int i = 0; i < 20; i++) {
            // int rand = random.nextInt(50);
            
            // if (rand < 25) rand = 0;
            // else rand -= 24;
            int rand = 0;

            Card randomCard = Card.createCard(rand);
            farm.put(i, randomCard);
        }
    }

    public void InitializeDeck() {
        Random random = new Random();
        
        for (int i = 0; i < 6; i++) {
            // int rand = random.nextInt(50);
            
            // if (rand < 25) rand = 0;
            // else rand -= 24;
            int rand = 0;

            Card randomCard = Card.createCard(rand);
            activeDeck.put(i , randomCard);
        }
    }

    public int getGuldenCount() {
        return guldenCount;
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

        // System.out.println("Index " + index1 + ": " + cont1.get(index1).getCardName());
        // System.out.println("Index " + index2 + ": " + cont2.get(index2).getCardName());
    }

    public int getFarmFreeSlots() {
        int cardCount = 0;

        for (Card c : farm.values()) {
            if (c.getCardName() != CardConstants.CARD_EMPTY) cardCount++;
        }

        return PLAYER_FARM_SLOTS - cardCount;
    }

    public int getActiveDeckFreeSlots() {
        int cardCount = 0;

        for (Card c : activeDeck.values()) {
            if (c.getCardName() != CardConstants.CARD_EMPTY) cardCount++;
        }

        return PLAYER_ACTIVE_DECK_SLOTS - cardCount;
    }

    public void setFarmAt(int index, Card card) {
        farm.put(index, card);
    }

    public void setActiveDeckAt(int index, Card card) {
        activeDeck.put(index, card);
    }

    public void addToFarm(Card card) {
        for (int i = 0; i < PLAYER_FARM_SLOTS; i++) {
            if (farm.get(i).getCardName() == CardConstants.CARD_EMPTY) {
                farm.put(i, card);
                break;
            }
        }
    }

    public void addToActiveDeck(Card card) {
        for (int i = 0; i < PLAYER_ACTIVE_DECK_SLOTS; i++) {
            if (activeDeck.get(i).getCardName() == CardConstants.CARD_EMPTY) {
                activeDeck.put(i, card);
                break;
            }
        }
    }
}
