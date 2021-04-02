package com.gpesce.challenge.superheroapi.service;

import com.gpesce.challenge.superheroapi.service.dto.CreateSuperheroRequestDTO;

import java.util.function.Consumer;

@FunctionalInterface
public interface CreateSuperheroRequestValidator extends Consumer<CreateSuperheroRequestDTO> {
}
