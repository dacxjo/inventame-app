package com.ubpis.inventame.data.model;

public class Reminder {

    private Product product;
    private String ID;
    private String desc;

    public Reminder(Product product, String ID, String desc) {
        this.product = product;
        this.ID = ID;
        this.desc = desc;
    }
}
