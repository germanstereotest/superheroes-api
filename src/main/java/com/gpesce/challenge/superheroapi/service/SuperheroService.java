package com.gpesce.challenge.superheroapi.service;

import com.gpesce.challenge.superheroapi.controller.dto.SuperheroResponseDTO;
import com.gpesce.challenge.superheroapi.service.dto.CreateSuperheroRequestDTO;
import com.gpesce.challenge.superheroapi.service.dto.UpdateSuperheroRequestDTO;

import java.util.List;

public interface SuperheroService {

    List<SuperheroResponseDTO> findAll();
    List<SuperheroResponseDTO> findAllLikeName(String name);
    SuperheroResponseDTO findById(Long id);
    SuperheroResponseDTO create(CreateSuperheroRequestDTO superhero);
    SuperheroResponseDTO modify(UpdateSuperheroRequestDTO superhero);
    void remove(Long id);
}
