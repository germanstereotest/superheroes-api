package com.gpesce.challenge.superheroapi.exception;

public class SuperheroNotFoundException extends SuperheroException {

    public SuperheroNotFoundException(ErrorCodeEnum error) {
        super(error.name(), error.message);
    }
}
