package com.ubpis.inventame.model;

import java.util.ArrayList;

public class Inventame {

    private ListaEmpleados lista;
    private Inventario inventario;
    private ArrayList<Recordatorio> recordatorios;

    public Inventame(){
        this.inventario = new Inventario();
        this.lista = new ListaEmpleados();
        this.recordatorios = new ArrayList<>();
    }

    public Inventame(ListaEmpleados lista, Inventario inventario, ArrayList<Recordatorio> recordatorios){
        this.inventario = inventario;
        this.lista = lista;
        this.recordatorios = recordatorios;
    }

    public ListaEmpleados getEmpleados() {
        return lista;
    }

    public void setLista(ListaEmpleados lista) {
        this.lista = lista;
    }
}
