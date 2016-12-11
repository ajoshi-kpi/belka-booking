package com.example.controllers;

public class NoSuchUserException extends Exception {
    public NoSuchUserException() {
        super("No such user");
    }
}
