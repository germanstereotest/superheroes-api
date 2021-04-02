package com.gpesce.challenge.superheroapi.service;

import com.gpesce.challenge.superheroapi.controller.dto.SuperheroResponseDTO;
import com.gpesce.challenge.superheroapi.service.dto.CreateSuperheroRequestDTO;

import java.util.function.Function;

@FunctionalInterface
public interface CreateSuperhero extends Function<CreateSuperheroRequestDTO, SuperheroResponseDTO> {
}
