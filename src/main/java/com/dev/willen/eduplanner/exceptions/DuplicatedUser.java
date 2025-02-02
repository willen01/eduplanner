package com.dev.willen.eduplanner.exceptions;

public class DuplicatedUser extends RuntimeException{
    public DuplicatedUser(String message) {
        super(message);
    }
}
