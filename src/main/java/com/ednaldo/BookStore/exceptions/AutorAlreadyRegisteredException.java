package com.ednaldo.BookStore.exceptions;

public class AutorAlreadyRegisteredException extends RuntimeException {

    public AutorAlreadyRegisteredException(String message) {
        super(message);
    }
}
