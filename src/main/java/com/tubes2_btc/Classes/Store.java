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

        // Inisialisasi jumlah produk berdasarkan produk awal
        for (Product product : products) {
            String productName = product.getCardName(); // Sesuaikan dengan cara mendapatkan identitas unik produk
            productCounts.put(productName, productCounts.getOrDefault(productName, 0) + 1);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProduct(int i) {
        return products.get(i);
    }

    public void addProduct(Product product) {
        String productName = product.getCardName(); // Sesuaikan dengan cara mendapatkan identitas unik produk

        if (productCounts.containsKey(productName)) {
            // Produk sudah ada, perbarui jumlahnya saja
            productCounts.put(productName, productCounts.get(productName) + 1);
        } else {
            // Produk baru, tambahkan ke daftar dan inisialisasi jumlahnya
            this.products.add(product);
            productCounts.put(productName, 1);
        }
    }

    public int getProductCount(String productName) {
        return productCounts.getOrDefault(productName, 0);
    }

    // Tambahkan metode lain jika diperlukan
}
