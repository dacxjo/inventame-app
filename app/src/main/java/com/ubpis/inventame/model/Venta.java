package com.ubpis.inventame.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Venta {

    private UUID uuid;
    private Date date;
    private ArrayList<Producto> lista;
    private double total;

    // private PerfilUsuario perfil; Podriamos registrar las ventas de cada usuario para mas datos...


    public Venta(ArrayList<Producto> lista, double total){
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

    public ArrayList<Producto> getLista() {
        return lista;
    }

    public double getTotal() {
        return total;
    }
}
