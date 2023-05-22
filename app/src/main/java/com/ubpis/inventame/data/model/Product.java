package com.ubpis.inventame.data.model;

import com.google.firebase.Timestamp;

public class Product implements FirebaseDocument {

    private String id;
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

    public Product(String id, String name, String description, float price, int stock, String batch, String imageUrl, String businessId, String expirationDate){
        this.id = id;
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

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return this.stock;
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
