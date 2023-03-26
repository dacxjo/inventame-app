package com.ubpis.inventame.model;

import java.util.HashMap;

public class Inventory {

    private HashMap<String, Product> inventario;
    private HashMap<String, Sale> ventas;

    public Inventory(){
        this.inventario = new HashMap<>();
        this.ventas = new HashMap<>();
    }

    public Inventory(HashMap<String, Product> inventario, HashMap<String, Sale> ventas){
        this.inventario = inventario;
        this.ventas = ventas;
    }

    public void addNewProduct(Product product){
        inventario.put(product.getID(), product);
    }

    public void addProduct(int ID){
        Product product = inventario.get(ID);
        product.addStock(1);
    }
    public void removeProduct(int ID){
        Product product = inventario.get(ID);
        product.addStock(-1);
    }
}
