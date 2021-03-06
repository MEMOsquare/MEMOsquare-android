package com.estsoft.memosquare.models;

import android.graphics.Bitmap;

/**
 * Created by sun on 2016-10-31.
 */

public class FbUserModel {
    private String fb_id;
    private String name;
    private String email;
    private String picture_url;

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
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

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    @Override
    public String toString() {
        return "FbUserModel{" +
                "fb_id='" + fb_id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", picture_url='" + picture_url + '\'' +
                '}';
    }
}
