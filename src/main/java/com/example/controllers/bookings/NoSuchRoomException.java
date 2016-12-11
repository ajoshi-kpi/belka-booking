package com.example.controllers.bookings;

public class NoSuchRoomException extends Exception {
    public NoSuchRoomException() {
        super("No such room");
    }
}
