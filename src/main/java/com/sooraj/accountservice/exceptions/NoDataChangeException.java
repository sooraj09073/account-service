package com.sooraj.accountservice.exceptions;

public class NoDataChangeException extends RuntimeException {
    public NoDataChangeException(String message) {
        super(message);
    }
}
