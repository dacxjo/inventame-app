package com.ubpis.inventame.data.model;

public class Business extends User {

    private String companyName;
    private String description;
    private String cif;
    private String category;

    public Business() {
        super();
    }

    public String getTradename() {
        return companyName;
    }

    public void setTradename(String tradename) {
        this.companyName = tradename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
