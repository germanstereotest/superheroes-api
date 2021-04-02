package com.gpesce.challenge.superheroapi.service.impl;

import com.gpesce.challenge.superheroapi.exception.ErrorCodeEnum;
import com.gpesce.challenge.superheroapi.exception.SuperheroNotFoundException;
import com.gpesce.challenge.superheroapi.repository.SuperheroRepository;
import com.gpesce.challenge.superheroapi.service.UpdateSuperheroRequestValidator;
import com.gpesce.challenge.superheroapi.service.dto.UpdateSuperheroRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class UpdateSuperheroRequestValidatorImpl implements UpdateSuperheroRequestValidator {

    private final SuperheroRepository repository;

    public UpdateSuperheroRequestValidatorImpl(SuperheroRepository repository) {
        this.repository = repository;
    }

    @Override
    public void accept(UpdateSuperheroRequestDTO superheroRequestDTO) {
        if (!repository.existsById(superheroRequestDTO.getId())) {
            throw new SuperheroNotFoundException(ErrorCodeEnum.SUPERHERO_NOT_FOUND);
        }
    }
}
