package com.ubpis.inventame.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private ArrayList<Product> products;
    private Map<Product, Integer> quantities;

    public Cart(){
        products = new ArrayList<>();
        quantities = new HashMap<>();
    }

    public void addToCart(Product product, int quantity){
        if (!products.contains(product)) {
            products.add(product);
        }
        quantities.put(product, quantities.get(product) + quantity);
    }

    public void removeFromCart(Product product){
        products.remove(product);
        quantities.remove(product);
    }

    public double calculateTotal() {
        double total = 0;
        for (Product product : products) {
            int quantity = quantities.get(product);
            total += product.getPrice() * quantity;
        }
        return total;
    }


}
