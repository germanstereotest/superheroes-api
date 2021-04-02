package com.gpesce.challenge.superheroapi.service.impl;

import com.gpesce.challenge.superheroapi.controller.dto.SuperheroResponseDTO;
import com.gpesce.challenge.superheroapi.exception.ErrorCodeEnum;
import com.gpesce.challenge.superheroapi.exception.SuperheroAlreadyExistException;
import com.gpesce.challenge.superheroapi.exception.SuperheroNotFoundException;
import com.gpesce.challenge.superheroapi.model.Superhero;
import com.gpesce.challenge.superheroapi.repository.SuperheroRepository;
import com.gpesce.challenge.superheroapi.service.CreateSuperhero;
import com.gpesce.challenge.superheroapi.service.DeleteSuperhero;
import com.gpesce.challenge.superheroapi.service.SuperheroService;
import com.gpesce.challenge.superheroapi.service.UpdateSuperhero;
import com.gpesce.challenge.superheroapi.service.dto.CreateSuperheroRequestDTO;
import com.gpesce.challenge.superheroapi.service.dto.UpdateSuperheroRequestDTO;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SuperheroServiceImpl implements SuperheroService {

    private final SuperheroRepository repository;
    private final ModelMapper modelMapper;

    public SuperheroServiceImpl(SuperheroRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SuperheroResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(element -> modelMapper.map(element, SuperheroResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SuperheroResponseDTO> findAllLikeName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(element -> modelMapper.map(element, SuperheroResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SuperheroResponseDTO findById(Long id) {
        Optional<Superhero> result = repository.findById(id);
        if (!result.isPresent()){
            throw new SuperheroNotFoundException(ErrorCodeEnum.SUPERHERO_NOT_FOUND);
        }
        return modelMapper.map(result.get(), SuperheroResponseDTO.class);
    }

    @Override
    public SuperheroResponseDTO create(CreateSuperheroRequestDTO superhero) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SuperheroResponseDTO modify(UpdateSuperheroRequestDTO superhero) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Long id) {
        throw new UnsupportedOperationException();
    }

}
