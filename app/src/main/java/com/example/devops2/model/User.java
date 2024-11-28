package com.example.devops2.model;

public class User {

    private String firstName, lastName, phone, ops, username;

    public User() {
    }

    public User(String firstName, String lastName, String phone, String ops, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.ops = ops;
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOps() {
        return ops;
    }

    public void setOps(String ops) {
        this.ops = ops;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
