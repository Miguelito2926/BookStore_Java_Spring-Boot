package com.ednaldo.BookStore.exceptions;

public class OperationNotAllowedException extends RuntimeException {

    public OperationNotAllowedException(String message) {
        super(message);
    }
}
