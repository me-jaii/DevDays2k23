package com.example.devdays;

public class User {

    String email_id;
    String name;

    public User() {
    }

    public User(String email_id, String name) {
        this.email_id = email_id;
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
