package com.zeolink.med_manager.datasource;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by isaac on 4/16/2018.
 */

public class User implements Parcelable {
    private String id;
    private String name;
    private String email;
    private String imageUrl;

    public User() {
    }

    public User(String id, String name, String email, String imageUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }
    protected User(Parcel input) {
        id = input.readString();
        name = input.readString();
        email = input.readString();
        imageUrl = input.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel input) {

            return new User(input);
        }

        @Override
        public User[] newArray(int size) {

            return new User[size];
        }
    };

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeString(id);
        destination.writeString(name);
        destination.writeString(email);
        destination.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}