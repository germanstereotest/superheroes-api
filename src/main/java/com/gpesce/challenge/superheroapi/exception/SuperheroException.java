package com.gpesce.challenge.superheroapi.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuperheroException extends RuntimeException {

    private ErrorCodeEnum error;

    public SuperheroException(ErrorCodeEnum error) {
        super(error.message);
        this.error = error;
    }

}
