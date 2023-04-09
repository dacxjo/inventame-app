package com.ubpis.inventame.data.model;

public class Category {

    private String id;
    private String title;
    private String description;
    private String img;

    private boolean isSelected;

    public Category(String id, String title, String desc, String img,boolean isSelected){
        this.id = id;
        this.title = title;
        this.description = desc;
        this.img = img;
        this.isSelected =isSelected;
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

    public void setSelected(){
        this.isSelected = true;
    }

    public void setUnselected(){
        this.isSelected = false;
    }

    public void toggleSelection(){
        this.isSelected = !this.isSelected;
    }

    public boolean getIsSelected(){
        return this.isSelected;
    }
}
