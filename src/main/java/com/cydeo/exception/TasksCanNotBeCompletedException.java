package com.cydeo.exception;

public class TasksCanNotBeCompletedException extends RuntimeException {

    public TasksCanNotBeCompletedException(String message) {
        super(message);
    }

}
