package com.example.noblesse;

public class CurrentUser {

    private static User user;

    public static void setUser(String email, String username, String password){
        user = new User(email, username, password);
    }

    public static User getUser() {
        return user;
    }
}
