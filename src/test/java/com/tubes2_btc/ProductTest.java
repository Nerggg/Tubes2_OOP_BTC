package com.tubes2_btc;

import com.tubes2_btc.Classes.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void testCreateProduct(){
        Product product = new Product("test","path",10,5,20,"Carnivore Food");
        assertEquals("test",product.getCardName());
        assertEquals("path",product.getCardPath());
        assertEquals(10,product.getSellPrice());
        assertEquals(5,product.getAddedWeight());
        assertEquals(20,product.getHarvestAge());
        assertEquals("Carnivore Food",product.getFoodType());
    }
}