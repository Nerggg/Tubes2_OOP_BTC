package com.tubes2_btc.Classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Store {
    private List<Product> products;
    private Map<String, Integer> productCounts;

    public Store(List<Product> products) {
        this.products = products;
        this.productCounts = new HashMap<>();

        // Inisialisasi jumlah produk dengan 0 untuk setiap produk awal
        for (Product product : products) {
            String productName = product.getCardName(); // Sesuaikan dengan cara mendapatkan identitas unik produk
            productCounts.put(productName, 0);
        }
    }

    public Store(List<Product> products, int jumlah) {
        this.products = products;
        this.productCounts = new HashMap<>();

        // Inisialisasi jumlah produk dengan 0 untuk setiap produk awal
        for (Product product : products) {
            String productName = product.getCardName(); // Sesuaikan dengan cara mendapatkan identitas unik produk
            productCounts.put(productName, jumlah);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProduct(int i) {
        return products.get(i);
    }

    public void addProduct(Product product, int jumlah) {
        String productName = product.getCardName(); // Sesuaikan dengan cara mendapatkan identitas unik produk

        if (productCounts.containsKey(productName)) {
            // Produk sudah ada, perbarui jumlahnya saja
            productCounts.put(productName, productCounts.get(productName) + jumlah);
        } else {
            // Produk baru, tambahkan ke daftar dan inisialisasi jumlahnya
            this.products.add(product);
            productCounts.put(productName, jumlah);
        }
    }

    public int getProductCount(String productName) {
        return productCounts.getOrDefault(productName, 0);
    }
}
