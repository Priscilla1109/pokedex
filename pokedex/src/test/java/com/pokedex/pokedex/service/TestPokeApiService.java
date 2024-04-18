package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.model.EvolutionChain;
import com.pokedex.pokedex.model.EvolutionPokemon;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonResquest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TestPokeApiService {
    @InjectMocks
    private PokeApiService pokeApiService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

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

        PokemonResquest pokemonSuch = pokeApiService.getPokemonByNumber(1L);

        assertNotNull(pokemonSuch);
        assertEquals("bulbasaur", pokemonSuch.getName());
    }

    @Test
    public void testGetPokemonNameOrNumber() throws Exception{
        String mockResponse = "{\"number\": 1, \"name\": \"bulbasaur\"}";

        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        PokemonResquest result = pokeApiService.getPokemonNameOrNumber("bulbasaur");

        assertEquals("bulbasaur", result.getName());
        assertEquals(Long.valueOf(1), result.getNumber());
    }

    @Test
    public void testGetEvolutionByName() throws Exception{
        String mockResponse = "{\"number\": 1, \"name\": \"bulbasaur,\" \"evolution_chain\": {\"url\": \"https://pokeapi.co/api/v2/evolution-chain/1/\"} }";

        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        EvolutionPokemon result = pokeApiService.getEvolutionByName("bulbasaur");

        assertEquals("bulbasaur", result.());
        assertEquals(Long.valueOf(1), result.getNumber());
        assertEquals("https://pokeapi.co/api/v2/evolution-chain/1/", result.ge);
    }
}
