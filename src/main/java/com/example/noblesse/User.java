package com.example.noblesse;

public class User {

    private String username;
    private String email;

    // Default constructor (required for Firestore deserialization)
    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getters and setters (required for Firestore serialization)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
