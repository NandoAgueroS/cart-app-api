package com.cart_app.cart_service.exception;

import com.cart_app.cart_service.dto.ErrorMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> productNotFoundExeption(ProductNotFoundException exception){
        ErrorMessage message = ErrorMessage.builder()
                .status(HttpStatus.NOT_FOUND)
                .error("Resource Not Found")
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> cartNotFoundExeption(CartNotFoundException exception){
        ErrorMessage message = ErrorMessage.builder()
                .status(HttpStatus.NOT_FOUND)
                .error("Resource Not Found")
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> invalidArgumentExeption(InvalidArgumentException exception){
        ErrorMessage message = ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST)
                .error("Invalid Argument")
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> duplicateEntry(DataIntegrityViolationException exception){
        String error = "Data integrity violation";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (exception.getCause() instanceof java.sql.SQLIntegrityConstraintViolationException)
            error = "Duplicate entry";
            httpStatus = HttpStatus.CONFLICT;

        ErrorMessage message = ErrorMessage.builder()
                .status(httpStatus)
                .error("Duplicate Entry")
                .message("The resource already exists or violates a data integrity rule")
                .build();
        return ResponseEntity.status(httpStatus).body(message);
    }
}
