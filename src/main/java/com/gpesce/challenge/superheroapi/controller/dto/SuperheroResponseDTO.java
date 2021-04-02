package com.gpesce.challenge.superheroapi.controller.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperheroResponseDTO implements Serializable {

    private Long id;
    private String name;
    private String description;

}
