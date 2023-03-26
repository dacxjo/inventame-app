package com.ubpis.inventame.model;

public class User {

    private String email;
    private String name;
    private String surname;
    private String pwd;
    private Integer id_inventame;
    //private PerfilUsuario perfil;

    public User(String email, String name, String surname, String pwd, Integer id_inventame) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.pwd = pwd;
        this.id_inventame = id_inventame;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getId_inventame() {
        return id_inventame;
    }

    public void setId_inventame(Integer id_inventame) {
        this.id_inventame = id_inventame;
    }
}
