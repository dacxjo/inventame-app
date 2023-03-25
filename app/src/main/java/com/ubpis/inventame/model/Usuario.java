package com.ubpis.inventame.model;

public class Usuario {

    private String email;
    private String nombre;
    private String apellido;
    private String pwd;
    private Integer id_inventame;
    //private PerfilUsuario perfil;

    public Usuario(String email, String nombre, String apellido, String pwd, Integer id_inventame) {
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.pwd = pwd;
        this.id_inventame = id_inventame;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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
