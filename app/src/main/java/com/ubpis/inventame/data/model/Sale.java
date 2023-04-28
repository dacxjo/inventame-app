package com.ubpis.inventame.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Sale {

    private UUID uuid;
    private Date date;
    private ArrayList<Product> lista;
    private double total;

    // private PerfilUsuario perfil; Podriamos registrar las ventas de cada usuario para mas datos...


    public Sale(ArrayList<Product> lista, double total){
        this.lista = lista;
        this.uuid = UUID.randomUUID();
        this.date = new Date();
        this.total = total;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Product> getLista() {
        return lista;
    }

    public double getTotal() {
        return total;
    }
}
