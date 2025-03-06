package com.ednaldo.BookStore.exceptions;

public class AutorNotFoundException extends RuntimeException {

    public AutorNotFoundException(String message) {
        super(message);
    }
}
