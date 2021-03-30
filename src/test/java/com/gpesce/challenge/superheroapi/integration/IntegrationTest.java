package com.gpesce.challenge.superheroapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpesce.challenge.superheroapi.controller.SuperheroRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Testing Find all Found: 200")
    @Test
    public void testGetAllSuperherosFound() throws Exception{

        mockMvc.perform(get("/superheroes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @DisplayName("Testing Find all Empty: 200")
    @Test
    public void testGetAllSuperherosEmpty() throws Exception{

        mockMvc.perform(get("/superheroes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @DisplayName("Testing Find one Found: 200")
    @Test
    public void testGetSuperheroFound() throws Exception{

        mockMvc.perform(get("/superheroes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id" ).value("1"))
                .andExpect(jsonPath("$.name").value("Batman"));
    }

    @DisplayName("Testing Find one Not found: 404")
    @Test
    public void testGetSuperheroNotFound() throws Exception{

        mockMvc.perform(get("/superheroes/999999"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Testing Creation OK: 201")
    @Test
    public void testPostSuperheroCreated() throws Exception{

        SuperheroRequestDTO superhero = new SuperheroRequestDTO();
        superhero.setName("Batman");
        superhero.setDescription("DC Hero");

        mockMvc.perform(post("/superheroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(superhero)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id" ).value("1"));
    }

    @DisplayName("Testing Creation BAD: 400")
    @Test
    public void testPostSuperheroBadRequest() throws Exception{

        mockMvc.perform(post("/superheroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new SuperheroRequestDTO())))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Testing Creation not unique: 409")
    @Test
    public void testPostSuperheroExist() throws Exception{

        SuperheroRequestDTO superhero = new SuperheroRequestDTO();
        superhero.setName("Batman");
        superhero.setDescription("DC Hero");

        mockMvc.perform(post("/superheroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(superhero)))
                .andExpect(status().isConflict());
    }

    @DisplayName("Testing Modification OK: 200")
    @Test
    public void testPutSuperheroFound() throws Exception{

        SuperheroRequestDTO superhero = new SuperheroRequestDTO();
        superhero.setName("Batman");
        superhero.setDescription("DC Superhero");

        mockMvc.perform(put("/superheroes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id" ).value("1"))
                .andExpect(jsonPath("$.name").value("Batman"))
                .andExpect(jsonPath("$.description").value("DC Superhero"));
    }

    @DisplayName("Testing Modification BAD: 400")
    @Test
    public void testPutSuperheroBadRequest() throws Exception{

        mockMvc.perform(put("/superheroes/1"))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Testing Modification Not Found: 404")
    @Test
    public void testPutSuperheroNotFound() throws Exception{

        SuperheroRequestDTO superhero = new SuperheroRequestDTO();
        superhero.setName("Batman");
        superhero.setDescription("DC Superhero");

        mockMvc.perform(put("/superheroes/999999"))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Testing Removing OK: 200")
    @Test
    public void testDeleteSuperheroFound() throws Exception{

        mockMvc.perform(delete("/superheroes/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("Testing Removing Not Found: 404")
    @Test
    public void testDeleteSuperheroNotFound() throws Exception{

        mockMvc.perform(delete("/superheroes/1"))
                .andExpect(status().isNotFound());
    }

    private String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException("error when wrap json");
        }
    }

}
