package com.ubpis.inventame.data.model;

import com.google.firebase.Timestamp;

public class Category implements FirebaseDocument {

    private String id;
    private final String title;
    private final String description;
    private final String img;
    private Timestamp createdAt;
    private Timestamp deletedAt;
    private boolean isSelected;


    public Category(String id, String title, String desc, String img, Timestamp createdAt, Timestamp deletedAt) {
        this.id = id;
        this.title = title;
        this.description = desc;
        this.img = img;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.isSelected = false;
    }

    public Category(String id, String title, String desc, String img, Timestamp createdAt, Timestamp deletedAt, boolean isSelected) {
        this.id = id;
        this.title = title;
        this.description = desc;
        this.img = img;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.isSelected = isSelected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImg() {
        return img;
    }

    public void setSelected() {
        this.isSelected = true;
    }

    public void setUnselected() {
        this.isSelected = false;
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }


    @Override
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    @Override
    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }
}
