package com.gpesce.challenge.superheroapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SuperheroErrorApiResponse {

    private String code;
    private String message;
}
