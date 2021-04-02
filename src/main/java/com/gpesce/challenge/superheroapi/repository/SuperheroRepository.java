package com.gpesce.challenge.superheroapi.repository;

import com.gpesce.challenge.superheroapi.model.Superhero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuperheroRepository extends JpaRepository<Superhero, Long> {

    List<Superhero> findByNameContainingIgnoreCase(String name);
    Optional<Superhero> findByName(String name);
}
