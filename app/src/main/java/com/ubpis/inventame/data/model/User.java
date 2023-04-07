package com.ubpis.inventame.data.model;

public class User {
    private String email;
    private String name;
    private String surname;
    private String mPictureURL; // Url d'Internet, no la foto en si

    public User(String email, String name, String surname, String pictureURL) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.mPictureURL = pictureURL;
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


    public String getmPictureURL() {
        return mPictureURL;
    }

    public void setmPictureURL(String mPictureURL) {
        this.mPictureURL = mPictureURL;
    }

    public String getPwd(){
        return "00000";
    }
}
