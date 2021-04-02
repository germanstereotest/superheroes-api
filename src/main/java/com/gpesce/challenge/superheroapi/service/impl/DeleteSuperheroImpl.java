package com.gpesce.challenge.superheroapi.service.impl;

import com.gpesce.challenge.superheroapi.repository.SuperheroRepository;
import com.gpesce.challenge.superheroapi.service.DeleteSuperhero;
import com.gpesce.challenge.superheroapi.service.DeleteSuperheroRequestValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteSuperheroImpl implements DeleteSuperhero {

    private final DeleteSuperheroRequestValidator deleteSuperheroRequestValidator;
    private final SuperheroRepository repository;
    private final ModelMapper modelMapper;

    public DeleteSuperheroImpl(DeleteSuperheroRequestValidator deleteSuperheroRequestValidator,
                               SuperheroRepository repository, ModelMapper modelMapper) {
        this.deleteSuperheroRequestValidator = deleteSuperheroRequestValidator;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public void accept(Long id) {
        deleteSuperheroRequestValidator.accept(id);
        repository.deleteById(id);
    }

}
