package com.gpesce.challenge.superheroapi.exception;

public enum ErrorCodeEnum {

    SUPERHERO_NOT_FOUND("El súper héroe no fue encontrado"),
    SUPERHERO_NAME_MANDATORY("El campo name es mandatorio"),
    SUPERHERO_DUPLICATED("Ya existe un súper héroe con el nombre enviado");

    public final String message;

    private ErrorCodeEnum(String message) {
        this.message = message;
    }

}
