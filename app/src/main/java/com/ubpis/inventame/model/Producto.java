package com.ubpis.inventame.model;

public class Producto {

    private String ID;
    private String name;
    private String description;
    private double price;
    private int stock;

    public Producto(String ID, String name, String description, double price, int stock) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    // Getters y setters de atributos
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Añade 'x' al stock. Util para edicion inmediata.
    public void addStock(int x){
        this.stock+=x;
    }
}
