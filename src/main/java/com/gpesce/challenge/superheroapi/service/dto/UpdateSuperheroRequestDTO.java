package com.gpesce.challenge.superheroapi.service.dto;

import java.util.Objects;

public class UpdateSuperheroRequestDTO {

    private Long id;
    private String name;
    private String description;

    public UpdateSuperheroRequestDTO() {
    }

    public UpdateSuperheroRequestDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        UpdateSuperheroRequestDTO that = (UpdateSuperheroRequestDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
