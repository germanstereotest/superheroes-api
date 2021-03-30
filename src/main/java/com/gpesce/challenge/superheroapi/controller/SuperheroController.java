package com.gpesce.challenge.superheroapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class SuperheroController {

    @GetMapping(value = {"/superheroes"} )
    public ResponseEntity<Object> getSuperheroes(
            @RequestParam(required = false) Optional<String> name) {
        //throw new UnsupportedOperationException();
        SuperheroResponseDTO response = new SuperheroResponseDTO();
        response.setId(1L);
        response.setName("Batman");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/superheroes/{id}")
    public ResponseEntity<Object> getSuperheroesById(
            @PathVariable(required = true) int id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/superheroes")
    public ResponseEntity<Object> createSuperheroes(
            @RequestBody String superhero) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/superheroes/{id}")
    public ResponseEntity<Object> modifySuperheroesById(
            @PathVariable(required = true) int id,
            @RequestBody String superhero) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/superheroes/{id}")
    public ResponseEntity<Object> removeSuperheroesById(
            @PathVariable(required = true) int id) {
        throw new UnsupportedOperationException();
    }
}