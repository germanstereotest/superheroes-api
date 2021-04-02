package com.gpesce.challenge.superheroapi.service.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSuperheroRequestDTO implements Serializable {

    private Long id;
    private String name;
    private String description;

}
