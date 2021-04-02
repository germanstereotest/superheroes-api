package com.gpesce.challenge.superheroapi.service.dto;

import lombok.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSuperheroRequestDTO implements Serializable {

    private String name;
    private String description;

}
