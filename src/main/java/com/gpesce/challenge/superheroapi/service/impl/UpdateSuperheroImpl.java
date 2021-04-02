package com.gpesce.challenge.superheroapi.service.impl;

import com.gpesce.challenge.superheroapi.controller.dto.SuperheroResponseDTO;
import com.gpesce.challenge.superheroapi.model.Superhero;
import com.gpesce.challenge.superheroapi.repository.SuperheroRepository;
import com.gpesce.challenge.superheroapi.service.UpdateSuperhero;
import com.gpesce.challenge.superheroapi.service.UpdateSuperheroRequestValidator;
import com.gpesce.challenge.superheroapi.service.dto.UpdateSuperheroRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UpdateSuperheroImpl implements UpdateSuperhero {

    private final UpdateSuperheroRequestValidator updateSuperheroRequestValidator;
    private final SuperheroRepository repository;
    private final ModelMapper modelMapper;

    public UpdateSuperheroImpl(UpdateSuperheroRequestValidator updateSuperheroRequestValidator,
                               SuperheroRepository repository, ModelMapper modelMapper) {
        this.updateSuperheroRequestValidator = updateSuperheroRequestValidator;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public SuperheroResponseDTO apply(UpdateSuperheroRequestDTO superheroRequestDTO) {
        updateSuperheroRequestValidator.accept(superheroRequestDTO);
        Superhero entity = repository.getOne(superheroRequestDTO.getId());
        modelMapper.map(superheroRequestDTO, entity);

        return modelMapper.map(repository.save(entity), SuperheroResponseDTO.class);
    }
}
