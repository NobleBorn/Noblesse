package com.example.noblesse;

public class User {

    private String email;
    private String username;
    private String password;

    // Default constructor (required for Firestore deserialization)
    public User() {
    }

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
