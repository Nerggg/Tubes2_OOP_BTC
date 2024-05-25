package com.tubes2_btc;

import com.tubes2_btc.Classes.Animal;
import com.tubes2_btc.Classes.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class AnimalTest {

    @Test
    public void testCreateAnimal() {
        Animal animal = new Animal("test","/com/tubes2_btc/Pages/Images/Hewan/hiu darat.png",20,30,"Karnivora",2);
        assertEquals("test", animal.getCardName());
        assertEquals("/com/tubes2_btc/Pages/Images/Hewan/hiu darat.png", animal.getCardPath());
        assertEquals(20, animal.getWeight());
        assertEquals(30, animal.getHarvestWeight());
        assertEquals("Karnivora", animal.getAnimalType());
    }

    @Test
    void testAccelerate() {
        Animal animal = new Animal("test","/com/tubes2_btc/Pages/Images/Hewan/hiu darat.png",20,30,"Karnivora",2);
        animal.accelerate();
        assertEquals(28,animal.getWeight());
    }

    @Test
    void testDelay() {
        Animal animal = new Animal("test","/com/tubes2_btc/Pages/Images/Hewan/hiu darat.png",20,30,"Karnivora",2);
        animal.delay();
        assertEquals(15,animal.getWeight());
    }

    @Test
    void feed() {
        Animal animal = new Animal("test","/com/tubes2_btc/Pages/Images/Hewan/hiu darat.png",20,30,"Karnivora",2);
        Product product = new Product("prod","/com/tubes2_btc/Pages/Images/Hewan/hiu darat.png",20,5,5,"Carnivore Food");
        animal.feed(product);
        assertEquals(25,animal.getWeight());
    }
}