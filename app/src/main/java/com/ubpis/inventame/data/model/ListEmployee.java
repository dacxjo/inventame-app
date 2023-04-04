package com.ubpis.inventame.data.model;

import java.util.HashMap;
import java.util.List;

public class ListEmployee {

    private HashMap<String, User> list;

    public ListEmployee(){
        list = new HashMap<>();
    }

    public ListEmployee(List<User> allUsers) {
        this();
        for (User u: allUsers) {
            list.put(u.getEmail(), u);
            //u.setProfile(new PerfilUsuario());
        }
    }
    public User find(String email) {
        return list.get(email);
    }
}
