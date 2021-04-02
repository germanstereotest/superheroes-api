package com.gpesce.challenge.superheroapi.service;

import com.gpesce.challenge.superheroapi.service.dto.UpdateSuperheroRequestDTO;

import java.util.function.Consumer;

@FunctionalInterface
public interface UpdateSuperheroRequestValidator extends Consumer<UpdateSuperheroRequestDTO> {
}
