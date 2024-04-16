package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonResquest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPokeApiService {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PokeApiService pokeApiService;

    @Test
    public void testGetPokemonByNumber() throws JsonProcessingException {
        //Mockar a resposta a API
        String mockResponse = "{\"number\": 1, \"name\": \"bulbasaur\"}";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        //Configurando o Mock
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readTree(mockResponse)).thenReturn(new ObjectMapper().readTree(mockResponse));
        when(objectMapper.treeToValue(any(JsonNode.class), eq(Pokemon.class))).thenReturn(new Pokemon());

        PokemonResquest pokemonResquest = new PokemonResquest();

        PokemonResquest pokemonSuch = pokeApiService.getPokemonById(1L);

        assertNotNull(pokemonSuch);
        assertEquals("bulbasaur", pokemonSuch.getName());
    }
}
