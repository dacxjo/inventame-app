package com.ubpis.inventame.model;

public class Recordatorio {

    private Producto producto;
    private String ID;
    private String desc;

    public Recordatorio(Producto producto, String ID, String desc) {
        this.producto = producto;
        this.ID = ID;
        this.desc = desc;
    }
}
