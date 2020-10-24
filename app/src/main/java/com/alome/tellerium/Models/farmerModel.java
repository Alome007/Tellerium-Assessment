package com.alome.tellerium.Models;

public class farmerModel {
    String name,loctaion,id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoctaion() {
        return loctaion;
    }

    public void setLoctaion(String loctaion) {
        this.loctaion = loctaion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public farmerModel(String name, String loctaion, String id) {
        this.name = name;
        this.loctaion = loctaion;
        this.id = id;
    }
}
