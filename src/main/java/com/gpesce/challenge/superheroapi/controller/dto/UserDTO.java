package com.gpesce.challenge.superheroapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotNull
    @Size(min = 1, message = "firstName is mandatory")
    private String username;

    @NotBlank(message = "password is mandatory")
    private String password;

}
