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
        String productName = product.getCardName();

        if (productCounts.containsKey(productName)) {
            productCounts.put(productName, productCounts.get(productName) + jumlah);
        } else {
            this.products.add(product);
            productCounts.put(productName, jumlah);
        }
    }


    public int getProductCount(String productName) {
        return productCounts.getOrDefault(productName, 0);
    }

    public void setProductCounts(String productName, int jumlah) {
        productCounts.put(productName, jumlah);
    }

    public void setZeroCounts(){
        productCounts.put(CardConstants.CARD_SIRIP_HIU, 0);
        productCounts.put(CardConstants.CARD_SUSU, 0);
        productCounts.put(CardConstants.CARD_DAGING_DOMBA, 0);
        productCounts.put(CardConstants.CARD_DAGING_KUDA, 0);
        productCounts.put(CardConstants.CARD_TELUR, 0);
        productCounts.put(CardConstants.CARD_DAGING_BERUANG, 0);
        productCounts.put(CardConstants.CARD_JAGUNG, 0);
        productCounts.put(CardConstants.CARD_LABU, 0);
        productCounts.put(CardConstants.CARD_STROBERI, 0);
    }

    public void reduceProductCount(String productName, int quantity) {
        Product product = getProductByName(productName);
        if (product != null) {
            int currentCount = product.getCount();
            product.setCount(currentCount - quantity);
        }
    }

    private Product getProductByName(String productName) {
        for (Product product : products) {
            if (product.getCardName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

}
