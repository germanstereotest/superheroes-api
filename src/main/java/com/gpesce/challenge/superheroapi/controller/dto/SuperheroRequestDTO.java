package com.gpesce.challenge.superheroapi.controller.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperheroRequestDTO implements Serializable {

    private String name;
    private String description;

}
