package com.example.noblesse;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LogIn {

    private final FirestoreUser firestoreUser = new FirestoreUser();
    private ArrayList<String> userInfo;

    public LogIn(String username, String password) throws LogInException {
        userInformation(username);
        if (validateUser(username, password)){
            CurrentUser.setUser(userInfo.get(0), userInfo.get(1), userInfo.get(2));
        } else {
            throw new LogInException("Wrong username or password!");
        }

    }

    private void userInformation(String username) throws LogInException {
        try {
            userInfo = firestoreUser.getUserInfo(username);
        } catch (LogInException l){
            throw new LogInException(l.getMessage());
        } catch (ExecutionException | InterruptedException e){
            System.out.println(e.getMessage());
            throw new LogInException("Database disconnection!");
        }
    }

    private boolean validateUser(String username, String password) {
        return userInfo.get(1).equals(username) && userInfo.get(2).equals(password);
    }

}
