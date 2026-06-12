package com.bank.loans.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorResponse>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        List<ValidationErrorResponse> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.add(new ValidationErrorResponse(
                error.getField(),
                error.getDefaultMessage()
            ))
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
