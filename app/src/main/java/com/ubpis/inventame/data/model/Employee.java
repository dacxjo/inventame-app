package com.ubpis.inventame.data.model;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;

public class Employee extends User {

    private String name;
    private String lastname;
    private String documentNumber;
    private String businessId;

    public Employee() {
    }

    public Employee(Employee e){
        super();
        this.name = e.name;
        this.lastname = e.lastname;
        this.documentNumber = e.documentNumber;
        this.businessId = e.businessId;
        this.setId(e.getId());
        this.setEmail(e.getEmail());
        this.setType(e.getType());
        this.setCreatedAt(e.getCreatedAt());
        this.setDeletedAt(e.getDeletedAt());
        this.setImageUrl(e.getImageUrl());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullName() {
        return name + " " + lastname;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("lastname", lastname);
        map.put("documentNumber", documentNumber);
        map.put("businessId", getBusinessId());
        map.put("id", getId());
        map.put("email", getEmail());
        map.put("type", getType());
        map.put("createdAt", getCreatedAt());
        map.put("deletedAt", getDeletedAt());
        map.put("imageUrl", getImageUrl());
        return map;
    }
}
