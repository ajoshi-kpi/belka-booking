package com.example.controllers;

public class BookingException extends Exception {
    public BookingException() {
        super("Busy");
    }
}
