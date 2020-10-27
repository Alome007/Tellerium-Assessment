package com.alome.tellerium.Models;

public class farmerModel {
    String name,loctaion,id,avatar_url;
    boolean isLocal;

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

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

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public farmerModel(String name, String loctaion, String id, String avatar_url, boolean isLocal) {
        this.name = name;
        this.loctaion = loctaion;
        this.id = id;
        this.avatar_url = avatar_url;
        this.isLocal = isLocal;
    }
}
