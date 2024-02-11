package net.springtest.springboot.exception;

public class FloorNumberConflictException extends RuntimeException {

    public FloorNumberConflictException(String message) {
        super(message);
    }
}

