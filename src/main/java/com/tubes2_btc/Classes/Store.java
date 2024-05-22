package com.tubes2_btc.Classes;

import java.util.List;

public class Store {
    private List<Product> products;

    public  Store(List<Product> products){
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProduct(int i){
        return products.get(i);
    }

    public  void addProduct(Product product){
        this.products.add(product);
    }


}
