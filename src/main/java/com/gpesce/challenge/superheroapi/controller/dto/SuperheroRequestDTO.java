package com.gpesce.challenge.superheroapi.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperheroRequestDTO implements Serializable {

    @NotBlank
    private String name;
    private String description;

}
