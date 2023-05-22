package com.ubpis.inventame.data.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

public class Sale {

    private UUID uuid;
    private Date date;
    private boolean isManual;
    private boolean isFavourite;
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

    public static final Comparator<Sale> SALE_PRICE_LOW_HIGH_COMPARATOR = new Comparator<Sale>() {
        @Override
        public int compare(Sale o1, Sale o2) {
            Double o1Double = Double.valueOf(o1.getTotal());
            Double o2Double = Double.valueOf(o2.getTotal());
            return o1Double.compareTo(o2Double);
        }
    };

    public static final Comparator<Sale> SALE_PRICE_HIGH_LOW_COMPARATOR = new Comparator<Sale>() {
        @Override
        public int compare(Sale o1, Sale o2) {
            Double o1Double = Double.valueOf(o1.getTotal());
            Double o2Double = Double.valueOf(o2.getTotal());
            return o2Double.compareTo(o1Double);
        }
    };

    public static final Comparator<Sale> SALE_DATE_ASCENDING_COMPARATOR = new Comparator<Sale>() {
        @Override
        public int compare(Sale o1, Sale o2) {
            return o1.getDate().compareTo(o2.getDate());
        }
    };

    public static final Comparator<Sale> SALE_DATE_DESCENDING_COMPARATOR = new Comparator<Sale>() {
        @Override
        public int compare(Sale o1, Sale o2) {
            return o2.getDate().compareTo(o1.getDate());
        }
    };
}
