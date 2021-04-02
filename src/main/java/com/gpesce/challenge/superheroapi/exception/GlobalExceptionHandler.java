package com.gpesce.challenge.superheroapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { SuperheroNotFoundException.class })
    protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { SuperheroAlreadyExistException.class })
    protected ResponseEntity<Object> handleAlreadyExistException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = { SuperheroBadRequestException.class })
    protected ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}