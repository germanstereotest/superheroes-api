package com.gpesce.challenge.superheroapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpesce.challenge.superheroapi.controller.dto.SuperheroRequestDTO;
import com.gpesce.challenge.superheroapi.model.Superhero;
import com.gpesce.challenge.superheroapi.repository.SuperheroRepository;
import com.gpesce.challenge.superheroapi.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    private static final String TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwicm9sZXMiOiJST0xFX1VTRVIiLCJpYXQiOjE2MTc1MDQ2ODEsImV4cCI6MTYxNzUwODI4MX0.t0gF1cyQFqhxisE2SkmsecuLe14Rg0DQuXekTo4kLTg";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SuperheroRepository superheroRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("Testing Find all Found: 200")
    @Test
    public void testGetAllSuperherosFound() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.findAll()).thenReturn(mockGetAllSuperheroesResponse());

        mockMvc.perform(get("/api/superheroes").header("Authorization", TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(4)));
    }

    private Authentication mockAuth(String token) {
        return new UsernamePasswordAuthenticationToken("", token, AuthorityUtils.NO_AUTHORITIES);
    }

    @DisplayName("Testing Find all by name Found: 200")
    @Test
    public void testGetAllByNameSuperherosFound() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.findByNameContainingIgnoreCase(any())).thenReturn(mockGetAllSuperheroesByNameResponse());

        mockMvc.perform(get("/api/superheroes").header("Authorization", TOKEN)
                .queryParam("name", "man"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @DisplayName("Testing Find all Empty: 200")
    @Test
    public void testGetAllSuperherosEmpty() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.findAll()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/superheroes").header("Authorization", TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(0)));
    }

    @DisplayName("Testing Find one Found: 200")
    @Test
    public void testGetSuperheroFound() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.findById(anyLong())).thenReturn(mockGetOneSuperheroResponse());

        mockMvc.perform(get("/api/superheroes/1").header("Authorization", TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id" ).value("1"))
                .andExpect(jsonPath("$.name").value("Spiderman"));
    }

    @DisplayName("Testing Find one Not found: 404")
    @Test
    public void testGetSuperheroNotFound() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.findById(anyLong())).thenReturn(mockEmptySuperheroResponse());

        mockMvc.perform(get("/api/superheroes/999999").header("Authorization", TOKEN))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Testing Creation OK: 201")
    @Test
    public void testPostSuperheroCreated() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.save(any())).thenReturn(mockCreatedSuperheroResponse());

        mockMvc.perform(post("/api/superheroes").header("Authorization", TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new SuperheroRequestDTO("Batman", "DC Hero"))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id" ).value("5"));
    }

    @DisplayName("Testing Creation BAD: 400")
    @Test
    public void testPostSuperheroBadRequest() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));

        mockMvc.perform(post("/api/superheroes").header("Authorization", TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new SuperheroRequestDTO())))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Testing Creation not unique: 400")
    @Test
    public void testPostSuperheroExist() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.findByName(any())).thenReturn(mockGetOneSuperheroResponse());

        mockMvc.perform(post("/api/superheroes").header("Authorization", TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new SuperheroRequestDTO("Batman", "DC Hero"))))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Testing Modification OK: 200")
    @Test
    public void testPutSuperheroFound() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.existsById(any())).thenReturn(true);
        when(superheroRepository.getOne(any())).thenReturn(mockCreatedSuperheroResponse());
        when(superheroRepository.save(any())).thenReturn(mockUpdatedSuperheroResponse());

        mockMvc.perform(put("/api/superheroes/1").header("Authorization", TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new SuperheroRequestDTO("Batman", "DC Superhero"))))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id" ).value("5"))
                .andExpect(jsonPath("$.name").value("Batman"))
                .andExpect(jsonPath("$.description").value("DC Superhero"));
    }

    @DisplayName("Testing Modification name for another existing superhero Bad request: 400")
    @Test
    public void testPutSuperheroExisting() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.existsById(any())).thenReturn(true);
        when(superheroRepository.getOne(any())).thenReturn(mockCreatedSuperheroResponse());
        when(superheroRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc.perform(put("/api/superheroes/1").header("Authorization", TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new SuperheroRequestDTO("Batman", "DC Superhero"))))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Testing Modification BAD: 400")
    @Test
    public void testPutSuperheroBadRequest() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));

        mockMvc.perform(put("/api/superheroes/1").header("Authorization", TOKEN))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Testing Modification Not Found: 404")
    @Test
    public void testPutSuperheroNotFound() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.existsById(any())).thenReturn(false);

        mockMvc.perform(put("/api/superheroes/999999").header("Authorization", TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new SuperheroRequestDTO("Batman", "DC Superhero"))))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Testing Removing OK: 200")
    @Test
    public void testDeleteSuperheroFound() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.existsById(any())).thenReturn(true);
        doNothing().when(Mockito.spy(superheroRepository)).deleteById(any());

        mockMvc.perform(delete("/api/superheroes/1").header("Authorization", TOKEN))
                .andExpect(status().isNoContent());
    }

    @DisplayName("Testing Removing Not Found: 404")
    @Test
    public void testDeleteSuperheroNotFound() throws Exception{

        when(jwtTokenProvider.validateToken(any())).thenReturn(true);
        when(jwtTokenProvider.getAuthentication(any())).thenReturn(mockAuth(TOKEN));
        when(superheroRepository.existsById(any())).thenReturn(false);

        mockMvc.perform(delete("/api/superheroes/999999").header("Authorization", TOKEN))
                .andExpect(status().isNotFound());
    }

    private List<Superhero> mockGetAllSuperheroesResponse() {
        List<Superhero> mockedSuperheroes = new ArrayList<>();
        Superhero s1 = new Superhero();
        s1.setId(1L);
        s1.setName("Spiderman");
        s1.setDescription("Marvel Hero");
        mockedSuperheroes.add(s1);

        Superhero s2 = new Superhero();
        s2.setId(2L);
        s2.setName("Superman");
        s2.setDescription("DC Hero");
        mockedSuperheroes.add(s2);

        Superhero s3 = new Superhero();
        s3.setId(3L);
        s3.setName("Deadpool");
        s3.setDescription("Marvel Hero");
        mockedSuperheroes.add(s3);

        Superhero s4 = new Superhero();
        s4.setId(4L);
        s4.setName("Manolito el fuerte");
        mockedSuperheroes.add(s4);

        return mockedSuperheroes;
    }

    private List<Superhero> mockGetAllSuperheroesByNameResponse() {
        List<Superhero> mockedSuperheroes = new ArrayList<>();
        Superhero s1 = new Superhero();
        s1.setId(1L);
        s1.setName("Spiderman");
        s1.setDescription("Marvel Hero");
        mockedSuperheroes.add(s1);

        Superhero s2 = new Superhero();
        s2.setId(2L);
        s2.setName("Superman");
        s2.setDescription("DC Hero");
        mockedSuperheroes.add(s2);

        Superhero s3 = new Superhero();
        s3.setId(4L);
        s3.setName("Manolito el fuerte");
        mockedSuperheroes.add(s3);

        return mockedSuperheroes;
    }

    private Optional<Superhero> mockGetOneSuperheroResponse() {
        Superhero s1 = new Superhero();
        s1.setId(1L);
        s1.setName("Spiderman");
        s1.setDescription("Marvel Hero");

        return Optional.of(s1);
    }

    private Optional<Superhero> mockEmptySuperheroResponse() {
        return Optional.empty();
    }

    private Superhero mockCreatedSuperheroResponse() {
        Superhero created = new Superhero();
        created.setId(5L);
        created.setName("Batman");
        created.setDescription("DC Hero");

        return created;
    }

    private Superhero mockUpdatedSuperheroResponse() {
        Superhero created = new Superhero();
        created.setId(5L);
        created.setName("Batman");
        created.setDescription("DC Superhero");

        return created;
    }

    private String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException("error when wrap json");
        }
    }

}
