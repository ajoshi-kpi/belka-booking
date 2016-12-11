package com.example.controllers.registration;

public class UsernameExistsException extends Exception {
    public UsernameExistsException() {
        super("User with this username is already exists!");
    }
}
