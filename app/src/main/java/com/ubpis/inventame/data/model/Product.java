package com.ubpis.inventame.data.model;

public class Product {

    private String ID;
    private String name;
    private String description;

    private String mPrice;

    private String mStock;

    private String mPictureURL;
    private double price;
    private int stock;

    public Product(String ID, String name, String description, double price, int stock) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Product(String ID, String name, String mPrice, String mStock, String mPictureURL) {
        this.ID = ID;
        this.name = name;
        this.mPrice = mPrice;
        this.mStock = mStock;
        this.mPictureURL = mPictureURL;
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

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmStock() {
        return mStock;
    }

    public void setmStock(String mStock) {
        this.mStock = mStock;
    }

    public String getURL() {
        return mPictureURL;
    }

    public void setmPictureURL(String mPictureURL) {
        this.mPictureURL = mPictureURL;
    }

    // Añade 'x' al stock. Util para edicion inmediata.
    public void addStock(int x){
        this.stock+=x;
    }


}
