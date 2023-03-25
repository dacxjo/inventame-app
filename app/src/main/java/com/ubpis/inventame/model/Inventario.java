package com.ubpis.inventame.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventario {

    private HashMap<String,Producto> inventario;
    private HashMap<String,Venta> ventas;

    public Inventario(){
        this.inventario = new HashMap<>();
        this.ventas = new HashMap<>();
    }

    public Inventario(HashMap<String,Producto> inventario, HashMap<String,Venta> ventas){
        this.inventario = inventario;
        this.ventas = ventas;
    }

    public void addNewProduct(Producto producto){
        inventario.put(producto.getID(), producto);
    }

    public void addProduct(int ID){
        Producto producto = inventario.get(ID);
        producto.addStock(1);
    }
    public void removeProduct(int ID){
        Producto producto = inventario.get(ID);
        producto.addStock(-1);
    }
}
