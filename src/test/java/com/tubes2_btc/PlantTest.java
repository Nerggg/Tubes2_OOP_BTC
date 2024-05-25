package com.tubes2_btc;

import com.tubes2_btc.Classes.Plant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlantTest {

    @Test
    void testCreatePlant(){
        Plant plant = new Plant("test","path",5,10,5);
        assertEquals("test",plant.getCardName());
        assertEquals("path",plant.getCardPath());
        assertEquals(5,plant.getAge());
        assertEquals(10,plant.getHarvestAge());
    }

    @Test
    void testAccelerate() {
        Plant plant = new Plant("test","path",5,10,5);
        plant.accelerate();
        assertEquals(7,plant.getAge());
    }

    @Test
    void testDelay() {
        Plant plant = new Plant("test","path",5,10,5);
        plant.delay();
        assertEquals(3,plant.getAge());
    }

    @Test
    void testHarvest() {
        Plant plant = new Plant("test","path",5,10,5);
        plant.harvest();
    }
}