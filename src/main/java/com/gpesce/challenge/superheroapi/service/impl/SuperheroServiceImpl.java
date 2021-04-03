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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames={"superheroes"})
public class SuperheroServiceImpl implements SuperheroService {

    private final SuperheroRepository repository;
    private final ModelMapper modelMapper;
    private final CreateSuperhero createSuperhero;
    private final UpdateSuperhero updateSuperhero;
    private final DeleteSuperhero deleteSuperhero;

    public SuperheroServiceImpl(SuperheroRepository repository, ModelMapper modelMapper, CreateSuperhero createSuperhero,
                                UpdateSuperhero updateSuperhero, DeleteSuperhero deleteSuperhero) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.createSuperhero = createSuperhero;
        this.updateSuperhero = updateSuperhero;
        this.deleteSuperhero = deleteSuperhero;
    }

    @CachePut(value="superheroes", unless="#result.isEmpty()")
    @Override
    public List<SuperheroResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(element -> modelMapper.map(element, SuperheroResponseDTO.class))
                .collect(Collectors.toList());
    }

    @CachePut(value="superheroes", key="#name", unless="#result.isEmpty()")
    @Override
    public List<SuperheroResponseDTO> findAllLikeName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(element -> modelMapper.map(element, SuperheroResponseDTO.class))
                .collect(Collectors.toList());
    }

    @CachePut(value="superheroes", key="#id")
    @Override
    public SuperheroResponseDTO findById(Long id) {
        Optional<Superhero> result = repository.findById(id);
        if (result.isEmpty()){
            throw new SuperheroNotFoundException(ErrorCodeEnum.SUPERHERO_NOT_FOUND);
        }
        return modelMapper.map(result.get(), SuperheroResponseDTO.class);
    }

    @CacheEvict(value = "superheroes", allEntries = true)
    @Override
    public SuperheroResponseDTO create(CreateSuperheroRequestDTO superhero) {
        return createSuperhero.apply(superhero);
    }

    @CacheEvict(value = "superheroes", allEntries = true)
    @Override
    public SuperheroResponseDTO modify(UpdateSuperheroRequestDTO superhero) {
        SuperheroResponseDTO result;
        try {
            result = updateSuperhero.apply(superhero);
        } catch (DataIntegrityViolationException exception) {
            throw new SuperheroAlreadyExistException(ErrorCodeEnum.SUPERHERO_DUPLICATED);
        }
        return result;
    }

    @CacheEvict(value = "superheroes", allEntries = true)
    @Override
    public void remove(Long id) {
        deleteSuperhero.accept(id);
    }

}
