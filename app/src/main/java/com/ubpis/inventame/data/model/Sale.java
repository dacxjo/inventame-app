package com.ubpis.inventame.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Sale {

    private UUID uuid;
    private Date date;
    private ArrayList<Product> productList;
    private double total;

    // private PerfilUsuario perfil; Podriamos registrar las ventas de cada usuario para mas datos...

    public Sale(ArrayList<Product> productList, double total){
        this.productList = productList;
        this.uuid = UUID.randomUUID();
        this.date = new Date();
        this.total = total;
    }

    public Sale(double total){
        this.productList = new ArrayList<>();
        this.uuid = UUID.randomUUID();
        this.date = new Date();
        this.total = total;
    }

    public String getUuid() {
        return uuid.toString();
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Product> getLista() {
        return productList;
    }

    public double getTotal() {
        return total;
    }
}
