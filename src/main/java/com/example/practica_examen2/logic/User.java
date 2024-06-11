package com.example.practica_examen2.logic;

public class User {
    String email;
    String name;
    String password;
    String role;

    public User(String email, String name, String password, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User() {
        this.email = "";
        this.name = "";
        this.password = "";
        this.role = "";
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User clone(){
        return new User(this.email, this.name, this.password, this.role);
    }
}
