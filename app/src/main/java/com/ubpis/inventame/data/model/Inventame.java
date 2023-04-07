package com.ubpis.inventame.data.model;

import java.util.ArrayList;

public class Inventame {

    private ListEmployee lista;
    private Inventory inventory;
    private ArrayList<Reminder> reminders;

    public Inventame(){
        this.inventory = new Inventory();
        this.lista = new ListEmployee();
        this.reminders = new ArrayList<>();
    }

    public Inventame(ListEmployee lista, Inventory inventory, ArrayList<Reminder> reminders){
        this.inventory = inventory;
        this.lista = lista;
        this.reminders = reminders;
    }

    public ListEmployee getEmployees() {
        return lista;
    }

    public void setLista(ListEmployee lista) {
        this.lista = lista;
    }
}
