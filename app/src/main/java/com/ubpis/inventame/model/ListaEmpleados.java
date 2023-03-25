package com.ubpis.inventame.model;

import java.util.HashMap;
import java.util.List;

public class ListaEmpleados {

    private HashMap<String, Usuario> lista;

    public ListaEmpleados(){
        lista = new HashMap<>();
    }

    public ListaEmpleados(List<Usuario> allUsers) {
        this();
        for (Usuario u: allUsers) {
            lista.put(u.getEmail(), u);
            //u.setProfile(new PerfilUsuario());
        }
    }
    public Usuario find(String email) {
        return lista.get(email);
    }
}
