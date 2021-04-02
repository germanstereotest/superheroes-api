package com.gpesce.challenge.superheroapi.service;

import com.gpesce.challenge.superheroapi.controller.dto.SuperheroResponseDTO;
import com.gpesce.challenge.superheroapi.service.dto.UpdateSuperheroRequestDTO;

import java.util.function.Function;

@FunctionalInterface
public interface UpdateSuperhero extends Function<UpdateSuperheroRequestDTO, SuperheroResponseDTO> {
}
