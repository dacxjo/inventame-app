package com.ubpis.inventame.data.model;

public class Reminder {
    private Product producto;
    private String ID;
    private String notification;
    private String desc;
    private String mPictureURL;

    public Reminder(Product prod, String ID, String not, String desc) {
        this.producto = prod;
        this.ID = ID;
        this.notification = not;
        this.desc = desc;
    }

    public Reminder(String ID, String not, String desc, String url) {
        this.producto = null;
        this.ID = ID;
        this.notification = not;
        this.desc = desc;
        this.mPictureURL = url;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getmPictureURL() {
        return mPictureURL;
    }

    public void setmPictureURL(String mPictureURL) {
        this.mPictureURL = mPictureURL;
    }
}
