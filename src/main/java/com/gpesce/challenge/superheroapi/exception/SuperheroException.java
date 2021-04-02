package com.gpesce.challenge.superheroapi.exception;

public class SuperheroException extends RuntimeException {

    private String code;

    public SuperheroException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
