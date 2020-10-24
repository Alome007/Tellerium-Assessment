package com.alome.tellerium.Models;

public class mainModel {
    int count;
    String title;
    int icon;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public mainModel(int count, String title, int icon) {
        this.count = count;
        this.title = title;
        this.icon=icon;
    }
}
