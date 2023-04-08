package com.ubpis.inventame.data.model;

public class Category {

    private String title;
    private String description;
    private String img;

    public Category(String title, String desc, String img){
        this.title = title;
        this.description = desc;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
