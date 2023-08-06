package com.ubpis.inventame.data.model;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class Product implements FirebaseDocument {

    private String id;

    private String barcode;
    private String name;
    private String description;
    private float price;
    private int stock;
    private String batch;
    private String imageUrl;
    private String businessId;
    private boolean isExpired;
    private String expirationDate;
    private Timestamp createdAt;
    private Timestamp deletedAt;

    public Product() {
        this.isExpired = false;
    }

    public Product(String id, String barcode, String name, String description, float price, int stock, String batch, String imageUrl, String businessId, String expirationDate){
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.batch = batch;
        this.imageUrl = imageUrl;
        this.businessId = businessId;
        this.expirationDate = expirationDate;
        this.isExpired = false;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return this.price;
    }

    public String getPriceString() {
        return String.valueOf(this.price);
    }

    public void setPriceString(String price) {
        if (price.isEmpty())
            this.price = 0;
        else
            this.price = Float.parseFloat(price);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return this.stock;
    }

    public String getStockString() {
        return String.valueOf(this.stock);
    }

    public void setStockString(String stock) {
        if(stock.isEmpty())
            this.stock = 0;
        else
            this.stock = Integer.parseInt(stock);
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getBatch() {
        return this.batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBusinessId() {
        return this.businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isExpired() {
        return this.isExpired;
    }

    public void setExpired(boolean expired) {
        this.isExpired = expired;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("barcode", this.barcode);
        map.put("name", this.name);
        map.put("description", this.description);
        map.put("price", this.price);
        map.put("stock", this.stock);
        map.put("batch", this.batch);
        map.put("imageUrl", this.imageUrl);
        map.put("businessId", this.businessId);
        map.put("expirationDate", this.expirationDate);
        return map;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Timestamp getDeletedAt() {
        return this.deletedAt;
    }

    @Override
    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}
