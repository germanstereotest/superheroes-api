package com.gpesce.challenge.superheroapi.service.impl;

import com.gpesce.challenge.superheroapi.controller.dto.SuperheroResponseDTO;
import com.gpesce.challenge.superheroapi.model.Superhero;
import com.gpesce.challenge.superheroapi.repository.SuperheroRepository;
import com.gpesce.challenge.superheroapi.service.CreateSuperhero;
import com.gpesce.challenge.superheroapi.service.CreateSuperheroRequestValidator;
import com.gpesce.challenge.superheroapi.service.dto.CreateSuperheroRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class CreateSuperheroImpl implements CreateSuperhero {

    private final CreateSuperheroRequestValidator createSuperheroRequestValidator;
    private final SuperheroRepository repository;
    private final ModelMapper modelMapper;

    public CreateSuperheroImpl(CreateSuperheroRequestValidator createSuperheroRequestValidator,
                               SuperheroRepository repository, ModelMapper modelMapper) {
        this.createSuperheroRequestValidator = createSuperheroRequestValidator;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public SuperheroResponseDTO apply(CreateSuperheroRequestDTO superheroRequestDTO) {
        createSuperheroRequestValidator.accept(superheroRequestDTO);
        Superhero result = repository.save(modelMapper.map(superheroRequestDTO, Superhero.class));
        return modelMapper.map(result, SuperheroResponseDTO.class);
    }
}
