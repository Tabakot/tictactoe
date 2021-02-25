package ru.tab.tictactoe.exception;

public class IllegalIndexException extends Exception {

    private String message;

    public IllegalIndexException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
