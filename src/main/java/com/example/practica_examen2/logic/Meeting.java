package com.example.practica_examen2.logic;

public class Meeting {
    User owner;
    String id;
    String title;
    String date;
    String state;

    public Meeting(User owner, String id, String title, String date, String state) {
        this.owner = owner;
        this.id = id;
        this.title = title;
        this.date = date;
        this.state = state;
    }

    public Meeting() {
        this.owner = new User();
        this.id = "";
        this.title = "";
        this.date = "";
        this.state = "";
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Meeting clone(){
        return new Meeting(this.owner.clone(), this.id, this.title, this.date, this.state);
    }
}
