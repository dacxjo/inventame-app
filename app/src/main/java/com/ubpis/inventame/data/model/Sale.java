package com.ubpis.inventame.data.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Sale {

    private UUID uuid;
    private Date date;
    private ArrayList<CartItem> cartList;
    private double total;

    // private PerfilUsuario perfil; Podriamos registrar las ventas de cada usuario para mas datos...

    public Sale(ArrayList<CartItem> cartList, double total){
        this.cartList = cartList;
        this.uuid = UUID.randomUUID();
        this.date = new Date();
        this.total = total;
    }

    public Sale(double total){
        this.cartList = new ArrayList<>();
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

    public ArrayList<CartItem> getList() {
        return cartList;
    }

    public double getTotal() {
        return total;
    }

    public void addCartItem(CartItem cartItem){
        cartList.add(cartItem);
    }
}
