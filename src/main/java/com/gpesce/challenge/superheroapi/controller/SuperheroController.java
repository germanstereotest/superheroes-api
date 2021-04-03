package com.gpesce.challenge.superheroapi.controller;

import com.gpesce.challenge.superheroapi.controller.dto.SuperheroRequestDTO;
import com.gpesce.challenge.superheroapi.controller.dto.SuperheroResponseDTO;
import com.gpesce.challenge.superheroapi.profiler.ExecTimeMeasurement;
import com.gpesce.challenge.superheroapi.service.SuperheroService;
import com.gpesce.challenge.superheroapi.service.dto.CreateSuperheroRequestDTO;
import com.gpesce.challenge.superheroapi.service.dto.UpdateSuperheroRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SuperheroController {

    private final SuperheroService superheroService;

    public SuperheroController(SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    @ExecTimeMeasurement
    @GetMapping(value = {"/superheroes"} )
    public ResponseEntity<List<SuperheroResponseDTO>> getSuperheroes(
            @RequestParam(required = false) Optional<String> name) {

        List<SuperheroResponseDTO> response;
        if (name.isPresent()) {
            response = superheroService.findAllLikeName(name.get());
        } else {
            response = superheroService.findAll();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExecTimeMeasurement
    @GetMapping("/superheroes/{id}")
    public ResponseEntity<SuperheroResponseDTO> getSuperheroesById(
            @PathVariable(required = true) long id) {

        return new ResponseEntity<>(superheroService.findById(id), HttpStatus.OK);
    }

    @ExecTimeMeasurement
    @PostMapping("/superheroes")
    public ResponseEntity<SuperheroResponseDTO> createSuperheroes(
            @RequestBody SuperheroRequestDTO superhero) {

        return new ResponseEntity<>(
                superheroService.create(new CreateSuperheroRequestDTO(superhero.getName(), superhero.getDescription())),
                HttpStatus.CREATED);
    }

    @ExecTimeMeasurement
    @PutMapping("/superheroes/{id}")
    public ResponseEntity<SuperheroResponseDTO> modifySuperheroesById(
            @PathVariable(required = true) Long id,
            @RequestBody SuperheroRequestDTO superhero) {

        return new ResponseEntity<>(
                superheroService.modify(new UpdateSuperheroRequestDTO(id, superhero.getName(), superhero.getDescription())),
                HttpStatus.OK);
    }

    @ExecTimeMeasurement
    @DeleteMapping("/superheroes/{id}")
    public ResponseEntity removeSuperheroesById(
            @PathVariable(required = true) long id) {

        superheroService.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}