package com.example.controllers.bookings;

public class NoSuchUserException extends Exception {
    public NoSuchUserException() {
        super("No such user");
    }
}
