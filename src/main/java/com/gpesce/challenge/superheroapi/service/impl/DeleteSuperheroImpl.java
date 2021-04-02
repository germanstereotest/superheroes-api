package com.gpesce.challenge.superheroapi.service.impl;

import com.gpesce.challenge.superheroapi.repository.SuperheroRepository;
import com.gpesce.challenge.superheroapi.service.DeleteSuperhero;
import com.gpesce.challenge.superheroapi.service.DeleteSuperheroRequestValidator;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteSuperheroImpl implements DeleteSuperhero {

    private final DeleteSuperheroRequestValidator deleteSuperheroRequestValidator;
    private final SuperheroRepository repository;

    public DeleteSuperheroImpl(DeleteSuperheroRequestValidator deleteSuperheroRequestValidator,
                               SuperheroRepository repository) {
        this.deleteSuperheroRequestValidator = deleteSuperheroRequestValidator;
        this.repository = repository;
    }

    @Override
    @Transactional
    public void accept(Long id) {
        deleteSuperheroRequestValidator.accept(id);
        repository.deleteById(id);
    }

}
