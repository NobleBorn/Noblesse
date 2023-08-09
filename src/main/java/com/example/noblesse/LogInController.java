package com.example.noblesse;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class LogInController implements ITimer{

    @FXML Pane movingPane;
    @FXML Button signUpPageBttn;
    @FXML Button logInPageBttn;
    @FXML TextField usernameLog;
    @FXML PasswordField passLog;
    @FXML Button logInBttn;
    @FXML ImageView logImage;
    @FXML TextField mailSign;
    @FXML TextField userName;
    @FXML PasswordField passSign;
    @FXML Button signUpBttn;
    @FXML ImageView signImage;
    @FXML Label logErrorLabel;
    @FXML Label signErrorLabel;
    @FXML Label successLabel;

    private enum CurrentOperation {
        MOVE_RIGHT,
        MOVE_LEFT,
        SIGN_UP_SUCCESS,
        SIGN_UP_ERROR,
        LOG_IN_ERROR
    }

    private CurrentOperation operation;
    private final Timer timer = new Timer();
    private final UIVisibilityManager innerClass = new UIVisibilityManager();

    public void signUp(){
        String email = mailSign.getText().trim();
        String username = userName.getText().trim();
        String password = passSign.getText().trim();
        if (!(email.isEmpty() || username.isEmpty() || password.isEmpty())){
            try {
                new SignUp(email, username, password);
                operation = CurrentOperation.SIGN_UP_SUCCESS;
                timer.timer(this);
                innerClass.emptyFields(1);
            } catch (SignUpException s){
                signErrorLabel.setText(s.getMessage());
            }
        } else {
            operation = CurrentOperation.SIGN_UP_ERROR;
            timer.timer(this);
        }

    }

    public void logIn(){
        String username = usernameLog.getText().trim();
        String password = passLog.getText().trim();
        if (!(username.isEmpty() || password.isEmpty())){
            try {
                new LogIn(username, password);
                innerClass.emptyFields(2);
            } catch (LogInException l){
                logErrorLabel.setText(l.getMessage());
            }
        } else {
            operation = CurrentOperation.LOG_IN_ERROR;
            timer.timer(this);
        }
    }

    public void restrictSign(){
        restrictSpaces(passSign);
    }

    public void restrictLog(){
        restrictSpaces(passLog);
    }

    private void restrictSpaces(PasswordField passwordField) {
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains(" ")) {
                passwordField.setText(newValue.replaceAll(" ", ""));
            }
        });
    }

    public void moveRight(){
        innerClass.emptyFields(2);
        operation = CurrentOperation.MOVE_RIGHT;
        timer.timer(this);

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(movingPane);
        translate.setDuration(Duration.millis(2000));
        translate.setByX(820);
        translate.setAutoReverse(false);
        translate.play();
    }

    public void moveLeft(){
        innerClass.emptyFields(1);
        operation = CurrentOperation.MOVE_LEFT;
        timer.timer(this);

        TranslateTransition translate = new TranslateTransition();
        translate.setNode(movingPane);
        translate.setDuration(Duration.millis(2000));
        translate.setByX(-820);
        translate.setAutoReverse(false);
        translate.play();
    }

    @Override
    public void duringTimer() {
        switch (operation) {
            case MOVE_RIGHT -> innerClass.visibilityDuring("right");
            case MOVE_LEFT -> innerClass.visibilityDuring("left");
            case SIGN_UP_SUCCESS -> {
                successLabel.setText("Your account is created!");
                logInPageBttn.setDisable(true);
            }
            case SIGN_UP_ERROR -> {
                signErrorLabel.setText("Please fill all the fields!");
                logInPageBttn.setDisable(true);
            }
            case LOG_IN_ERROR -> {
                logErrorLabel.setText("Please fill all the fields!");
                signUpPageBttn.setDisable(true);
            }
        }
    }

    @Override
    public void afterTimer() {
        switch (operation) {
            case MOVE_RIGHT -> innerClass.visibilityAfter("right");
            case MOVE_LEFT -> innerClass.visibilityAfter("left");
            case SIGN_UP_SUCCESS -> {
                successLabel.setText("");
                logInPageBttn.setDisable(false);
            }
            case SIGN_UP_ERROR -> {
                signErrorLabel.setText("");
                logInPageBttn.setDisable(false);
            }
            case LOG_IN_ERROR -> {
                logErrorLabel.setText("");
                signUpPageBttn.setDisable(false);
            }
        }
    }

    private class UIVisibilityManager {
        private void emptyFields(int num){
            if (num == 1){
                mailSign.setText("");
                userName.setText("");
                passSign.setText("");
                successLabel.setText("");
                signErrorLabel.setText("");
            } else {
                usernameLog.setText("");
                passLog.setText("");
                logErrorLabel.setText("");
            }
        }

        private void buttonVisibility(String movement){
            if (movement.equals("right")) {
                signUpPageBttn.setVisible(false);
                signUpPageBttn.setDisable(true);
                logInPageBttn.setVisible(true);
                logInPageBttn.setDisable(false);
            } else {
                logInPageBttn.setVisible(false);
                logInPageBttn.setDisable(true);
                signUpPageBttn.setVisible(true);
                signUpPageBttn.setDisable(false);
            }
        }

        private void visibilityDuring(String movement){
            if (movement.equals("right")) {
                visibleDisableDuringTimer(usernameLog, passLog, logInBttn, logImage);
            } else {
                mailSign.setVisible(false);
                mailSign.setDisable(true);
                visibleDisableDuringTimer(userName, passSign, signUpBttn, signImage);
            }
        }

        private void visibleDisableDuringTimer(TextField usernameLog, PasswordField passLog, Button logInBttn, ImageView logImage) {
            usernameLog.setVisible(false);
            usernameLog.setDisable(true);
            passLog.setVisible(false);
            passLog.setDisable(true);
            logInBttn.setVisible(false);
            logInBttn.setDisable(true);
            logImage.setVisible(false);
            logImage.setDisable(true);
        }

        private void visibilityAfter(String movement){
            if (movement.equals("right")) {
                buttonVisibility(movement);
                mailSign.setVisible(true);
                mailSign.setDisable(false);
                visibleDisableAfterTimer(userName, passSign, signUpBttn, signImage);
            } else {
                buttonVisibility(movement);
                visibleDisableAfterTimer(usernameLog, passLog, logInBttn, logImage);
            }
        }

        private void visibleDisableAfterTimer(TextField usernameLog, PasswordField passLog, Button logInBttn, ImageView logImage) {
            usernameLog.setVisible(true);
            usernameLog.setDisable(false);
            passLog.setVisible(true);
            passLog.setDisable(false);
            logInBttn.setVisible(true);
            logInBttn.setDisable(false);
            logImage.setVisible(true);
            logImage.setDisable(false);
        }
    }
}
