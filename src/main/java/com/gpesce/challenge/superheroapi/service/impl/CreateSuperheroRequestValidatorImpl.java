package com.gpesce.challenge.superheroapi.service.impl;

import com.gpesce.challenge.superheroapi.exception.ErrorCodeEnum;
import com.gpesce.challenge.superheroapi.exception.SuperheroAlreadyExistException;
import com.gpesce.challenge.superheroapi.exception.SuperheroBadRequestException;
import com.gpesce.challenge.superheroapi.repository.SuperheroRepository;
import com.gpesce.challenge.superheroapi.service.CreateSuperheroRequestValidator;
import com.gpesce.challenge.superheroapi.service.dto.CreateSuperheroRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateSuperheroRequestValidatorImpl implements CreateSuperheroRequestValidator {

    private final SuperheroRepository repository;

    public CreateSuperheroRequestValidatorImpl(SuperheroRepository repository) {
        this.repository = repository;
    }

    @Override
    public void accept(CreateSuperheroRequestDTO superheroRequestDTO) {

        if (superheroRequestDTO.getName() ==  null || superheroRequestDTO.getName().isEmpty()) {
            throw new SuperheroBadRequestException(ErrorCodeEnum.SUPERHERO_NAME_MANDATORY);
        }
        if (repository.findByName(superheroRequestDTO.getName()).isPresent()) {
            throw new SuperheroAlreadyExistException(ErrorCodeEnum.SUPERHERO_DUPLICATED);
        }
    }

}
