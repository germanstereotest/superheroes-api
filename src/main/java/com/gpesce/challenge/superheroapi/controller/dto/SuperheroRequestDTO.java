package com.gpesce.challenge.superheroapi.controller.dto;

import java.io.Serializable;
import java.util.Objects;

public class SuperheroRequestDTO implements Serializable {

    private String name;
    private String description;

    public SuperheroRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuperheroRequestDTO that = (SuperheroRequestDTO) o;
        return name.equals(that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
