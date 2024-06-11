package com.example.practica_examen2.logic;

public class Contact {
    Meeting meeting;
    String id;
    String email;
    String name;

    public Contact(Meeting meeting, String id, String email, String name) {
        this.meeting = meeting;
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Contact() {
        this.meeting = new Meeting();
        this.id = "";
        this.email = "";
        this.name = "";
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Contact clone(){
        return new Contact(this.meeting.clone(), this.id, this.email, this.name);
    }
}
