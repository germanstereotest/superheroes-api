package com.gpesce.challenge.superheroapi.service.impl;

import com.gpesce.challenge.superheroapi.exception.ErrorCodeEnum;
import com.gpesce.challenge.superheroapi.exception.SuperheroAlreadyExistException;
import com.gpesce.challenge.superheroapi.exception.SuperheroNotFoundException;
import com.gpesce.challenge.superheroapi.repository.SuperheroRepository;
import com.gpesce.challenge.superheroapi.service.CreateSuperheroRequestValidator;
import com.gpesce.challenge.superheroapi.service.DeleteSuperheroRequestValidator;
import com.gpesce.challenge.superheroapi.service.dto.CreateSuperheroRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class DeleteSuperheroRequestValidatorImpl implements DeleteSuperheroRequestValidator {

    private final SuperheroRepository repository;

    public DeleteSuperheroRequestValidatorImpl(SuperheroRepository repository) {
        this.repository = repository;
    }

    @Override
    public void accept(Long id) {
        if (!repository.existsById(id)) {
            throw new SuperheroNotFoundException(ErrorCodeEnum.SUPERHERO_NOT_FOUND);
        }
    }
}
