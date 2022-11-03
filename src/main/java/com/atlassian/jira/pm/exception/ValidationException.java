package com.atlassian.jira.pm.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(Exception exception) {
        super(exception);
    }

    public ValidationException(String message) {
        super(message);
    }
}
