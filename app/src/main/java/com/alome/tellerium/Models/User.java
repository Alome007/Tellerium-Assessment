package com.alome.tellerium.Models;


import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by a_man on 5/4/2017.
 */

public class User implements Parcelable {

    private String id;
    private String name, image;

    public User() {
    }

    protected User(Parcel in) {

        id = in.readString();
        name = in.readString();
        image = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    public User(String id, String name, String status, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.image = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id.equals(user.id);
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }



    public void setName(String name) {
        this.name = name;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
    }
}
