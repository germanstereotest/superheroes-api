package com.gpesce.challenge.superheroapi.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class CreateSuperheroRequestDTO implements Serializable {

    private String name;
    private String description;

    public CreateSuperheroRequestDTO() {
    }

    public CreateSuperheroRequestDTO(String name, String description) {
        this.name = name;
        this.description = description;
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
        CreateSuperheroRequestDTO that = (CreateSuperheroRequestDTO) o;
        return name.equals(that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
