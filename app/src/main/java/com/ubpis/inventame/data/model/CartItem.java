package com.ubpis.inventame.data.model;

public class CartItem {
    String image;
    String nameProduct;
    int quantityNum;
    float totalPrice;

    public CartItem(String image, String nameProduct, int quantityNum, float totalPrice) {
        this.image = image;
        this.nameProduct = nameProduct;
        this.quantityNum = quantityNum;
        this.totalPrice = totalPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getQuantityNum() {
        return quantityNum;
    }

    public void setQuantityNum(int quantityNum) {
        this.quantityNum = quantityNum;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
