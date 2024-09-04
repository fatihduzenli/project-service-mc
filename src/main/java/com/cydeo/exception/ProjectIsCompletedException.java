package com.cydeo.exception;

public class ProjectIsCompletedException extends RuntimeException {

    public ProjectIsCompletedException(String message) {
        super(message);
    }

}
