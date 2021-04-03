package com.gpesce.challenge.superheroapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { SuperheroNotFoundException.class })
    protected ResponseEntity<SuperheroErrorApiResponse> handleNotFoundException(SuperheroException ex, WebRequest request) {
        return new ResponseEntity<SuperheroErrorApiResponse>(
                new SuperheroErrorApiResponse(ex.getError().name(),ex.getError().message),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { SuperheroAlreadyExistException.class, SuperheroBadRequestException.class })
    protected ResponseEntity<SuperheroErrorApiResponse> handleAlreadyExistException(SuperheroException ex, WebRequest request) {
        return new ResponseEntity<SuperheroErrorApiResponse>(
                new SuperheroErrorApiResponse(ex.getError().name(),ex.getError().message),
                HttpStatus.BAD_REQUEST);
    }

}