package com.estsoft.memosquare.models;

/**
 * Created by sun on 2016-10-31.
 */

public class FbUserModel {
    private String name;
    private String email;
    private String facebookID;
    private String gender;

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

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "FbUserModel{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", facebookID='" + facebookID + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
