package com.gpesce.challenge.superheroapi.exception;

public class SuperheroBadRequestException extends SuperheroException {

    public SuperheroBadRequestException(ErrorCodeEnum error) {
        super(error);
    }
}
