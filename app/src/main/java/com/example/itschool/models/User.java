package com.example.itschool.models;

public class User {
    private String name, email, pass, telephone;

    public User() {

    }
    public User(String name, String email, String pass, String telephone) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.telephone = telephone;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
