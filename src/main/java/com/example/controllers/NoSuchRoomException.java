package com.example.controllers;

public class NoSuchRoomException extends Exception {
    public NoSuchRoomException() {
        super("No such room");
    }
}
