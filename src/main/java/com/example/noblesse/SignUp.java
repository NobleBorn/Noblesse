package com.example.noblesse;

import java.util.concurrent.ExecutionException;
import org.apache.commons.validator.routines.EmailValidator;

public class SignUp {

    FirestoreUser firestoreUser = new FirestoreUser();

    public SignUp(String email, String username, String password) throws SignUpException{
        User user = new User(email, username, password);

        validLength(username, password);
        if (validUser(email, username)) {
            storeUser(user);
        } else {
            throw new SignUpException("Not a valid username/email.");
        }
    }

    private void storeUser(User user) throws SignUpException {
        boolean successful;
        try {
            successful = firestoreUser.storeUserInfo(user.getUsername(), user);
        } catch (ExecutionException | InterruptedException e){
            System.out.println(e.getMessage());
            throw new SignUpException("Disconnection with database.");
        }

        if (!successful){
            throw new SignUpException("This username already exists!");
        }
    }

    private boolean validUser(String email, String username) {
        EmailValidator emailValidator = EmailValidator.getInstance();

        boolean validEmail = emailValidator.isValid(email);
        boolean validUsername = username.matches("^[a-zA-Z0-9]+$");

        return validEmail && validUsername;
    }

    private void validLength(String username, String password) throws SignUpException {
        int usernameLength = username.length();
        int passwordLength = password.length();

        if (!(usernameLength > 2 && usernameLength <= 10)){
            throw new SignUpException("Username between 3-10 characters!");
        } else if (!(passwordLength > 3 && passwordLength <= 10)){
            throw new SignUpException("Password between 4-10 characters!");
        }
    }


}
