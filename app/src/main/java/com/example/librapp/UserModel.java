package com.example.librapp;

import java.util.List;

//our simple user
//TODO roles (admin?/worker?)
public class UserModel {
    private String uid;
    private String email;
    private String name;
    private String role;



    public UserModel(String uid, String email, String name, String role){
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
