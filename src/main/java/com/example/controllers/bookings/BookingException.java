package com.example.controllers.bookings;

public class BookingException extends Exception {
    public BookingException() {
        super("Busy");
    }
}
