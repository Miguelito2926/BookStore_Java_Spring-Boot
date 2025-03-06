package com.ednaldo.BookStore.handle;

import com.ednaldo.BookStore.exceptions.AutorAlreadyRegisteredException;
import com.ednaldo.BookStore.exceptions.AutorNotFoundException;
import com.ednaldo.BookStore.exceptions.ErrorResponse;
import com.ednaldo.BookStore.exceptions.OperationNotAllowedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AutorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAutorNotFoundException(AutorNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AutorAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleAutorAlreadyRegisteredException(AutorAlreadyRegisteredException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OperationNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleOperationNotAllowedException(OperationNotAllowedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

