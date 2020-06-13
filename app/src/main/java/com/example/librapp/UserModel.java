package com.example.librapp;

import java.util.List;

//our simple user
//TODO roles (admin?/worker?)
public class UserModel {
    private String uid;
    private String email;
    private String name;

    public UserModel(String uid, String email, String name){
        this.uid = uid;
        this.email = email;
        this.name = name;
    }

    public UserModel(){

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
