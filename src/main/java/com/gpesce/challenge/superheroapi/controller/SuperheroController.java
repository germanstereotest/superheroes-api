package com.gpesce.challenge.superheroapi.controller;

import com.gpesce.challenge.superheroapi.controller.dto.SuperheroRequestDTO;
import com.gpesce.challenge.superheroapi.controller.dto.SuperheroResponseDTO;
import com.gpesce.challenge.superheroapi.profiler.ExecTimeMeasurement;
import com.gpesce.challenge.superheroapi.service.SuperheroService;
import com.gpesce.challenge.superheroapi.service.dto.CreateSuperheroRequestDTO;
import com.gpesce.challenge.superheroapi.service.dto.UpdateSuperheroRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api")
public class SuperheroController {

    private final SuperheroService superheroService;

    public SuperheroController(SuperheroService superheroService) {
        this.superheroService = superheroService;
    }

    @Operation(summary = "Get all superheroes or a list of superheroes that meet search criteria by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found a list of superheroes",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroResponseDTO.class)) }) })
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

    @Operation(summary = "Get superhero by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the superheroes",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content) })
    @ExecTimeMeasurement
    @GetMapping("/superheroes/{id}")
    public ResponseEntity<SuperheroResponseDTO> getSuperheroesById(
            @PathVariable(required = true) long id) {

        return new ResponseEntity<>(superheroService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Create superhero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Superhero created",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroResponseDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid name", content = @Content),
            @ApiResponse(responseCode = "400", description = "Duplicated name", content = @Content) })
    @ExecTimeMeasurement
    @PostMapping("/superheroes")
    public ResponseEntity<SuperheroResponseDTO> createSuperheroes(
            @RequestBody SuperheroRequestDTO superhero) {

        return new ResponseEntity<>(
                superheroService.create(new CreateSuperheroRequestDTO(superhero.getName(), superhero.getDescription())),
                HttpStatus.CREATED);
    }

    @Operation(summary = "Modify superhero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Superhero modified",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Invalid id", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid name", content = @Content),
            @ApiResponse(responseCode = "400", description = "Duplicated name", content = @Content) })
    @ExecTimeMeasurement
    @PutMapping("/superheroes/{id}")
    public ResponseEntity<SuperheroResponseDTO> modifySuperheroesById(
            @PathVariable(required = true) Long id,
            @RequestBody SuperheroRequestDTO superhero) {

        return new ResponseEntity<>(
                superheroService.modify(new UpdateSuperheroRequestDTO(id, superhero.getName(), superhero.getDescription())),
                HttpStatus.OK);
    }

    @Operation(summary = "Delete superhero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Superhero deleted",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = SuperheroResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Invalid id", content = @Content) })
    @ExecTimeMeasurement
    @DeleteMapping("/superheroes/{id}")
    public ResponseEntity removeSuperheroesById(
            @PathVariable(required = true) long id) {

        superheroService.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}