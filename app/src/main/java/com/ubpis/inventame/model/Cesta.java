package com.ubpis.inventame.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cesta {

    private ArrayList<Producto> productos;
    private Map<Producto, Integer> cantidades;

    public Cesta(){
        productos = new ArrayList<>();
        cantidades = new HashMap<>();
    }

    public void addToCesta(Producto producto, int cantidad){
        if (!productos.contains(producto)) {
            productos.add(producto);
        }
        cantidades.put(producto, cantidades.get(producto) + cantidad);
    }

    public void removeFromCesta(Producto producto){
        productos.remove(producto);
        cantidades.remove(producto);
    }

    public double calcularTotal() {
        double total = 0;
        for (Producto producto : productos) {
            int cantidad = cantidades.get(producto);
            total += producto.getPrice() * cantidad;
        }
        return total;
    }


}
