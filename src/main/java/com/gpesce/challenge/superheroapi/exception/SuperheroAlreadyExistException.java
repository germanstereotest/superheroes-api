package com.gpesce.challenge.superheroapi.exception;

public class SuperheroAlreadyExistException extends SuperheroException {

    public SuperheroAlreadyExistException(ErrorCodeEnum error) {
        super(error);
    }
}
