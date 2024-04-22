package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonResponse;
import com.pokedex.pokedex.model.PokemonResquest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokeApiServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;


    @InjectMocks
    private PokeApiService pokeApiService;


    @Test
    public void testGetPokemonByNumber() throws JsonProcessingException {
        //Mockar a resposta a API
        String mockResponse = Constant.RESPONSE_BULBASAUR;
        PokemonResquest pokemonResquest = new PokemonResquest();
        pokemonResquest.setName(Constant.NAME_BULBASAUR);
        pokemonResquest.setNumber(Constant.NUMBER_BULBASAUR);

        ResponseEntity<String> responseEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);

        //Configurando o Mock
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readTree(mockResponse)).thenReturn(new ObjectMapper().readTree(mockResponse));
        when(objectMapper.treeToValue(any(JsonNode.class), eq(Pokemon.class))).thenReturn(new Pokemon());
        when(objectMapper.readValue(mockResponse, PokemonResponse.class)).thenReturn(new PokemonResponse());

        PokemonResponse pokemonSuch = pokeApiService.getPokemonByNumber(pokemonResquest.getNumber());

        assertNotNull(pokemonSuch);
        assertEquals(Constant.NAME_BULBASAUR, pokemonSuch.getName());
    }

    @Test
    public void testGetPokemonNameOrNumber() throws Exception{
        String mockResponse = Constant.RESPONSE_BULBASAUR;
        ResponseEntity<String> mockResponseEntity =  new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(mockResponseEntity);

        PokemonResquest mockPokemonResquest = new PokemonResquest();

        mockPokemonResquest.setName(Constant.NAME_BULBASAUR);
        mockPokemonResquest.setNumber(Constant.NUMBER_BULBASAUR);
        mockPokemonResquest.setType(Constant.TYPE_BULBASAUR);

        when(objectMapper.readValue(anyString(), eq(PokemonResquest.class))).thenReturn(mockPokemonResquest);

        PokemonResponse pokemonResponse = pokeApiService.getPokemonNameOrNumber("bulbasaur");

        assertEquals("bulbasaur", mockPokemonResquest.getName());
        assertEquals(1L, mockPokemonResquest.getNumber());
    }
}
