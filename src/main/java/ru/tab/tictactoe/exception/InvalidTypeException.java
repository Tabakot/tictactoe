package ru.tab.tictactoe.exception;

public class InvalidTypeException extends Exception {
    private String message;

    public InvalidTypeException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
