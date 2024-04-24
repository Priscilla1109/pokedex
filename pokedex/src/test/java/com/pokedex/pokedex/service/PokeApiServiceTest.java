package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;
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
        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setName(Constant.NAME_BULBASAUR);

        ResponseEntity<PokemonResponse> responseEntity = new ResponseEntity<>(pokemonResponse, HttpStatus.OK);

        //Configurando o Mock
        when(restTemplate.getForEntity(anyString(), eq(PokemonResponse.class))).thenReturn(responseEntity);

        PokemonResponse pokemonSuch = pokeApiService.getPokemonByNumber(pokemonResponse.getNumber());

        assertNotNull(pokemonSuch);
        assertEquals(Constant.NAME_BULBASAUR, pokemonSuch.getName());
    }

    @Test
    public void testGetPokemonNameOrNumber() throws Exception{
        //Mockar a resposta a API
        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setName(Constant.NAME_BULBASAUR);

        ResponseEntity<String> responseEntity = new ResponseEntity<>("{}", HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readValue(anyString(), eq(PokemonResponse.class))).thenReturn(pokemonResponse);

        PokemonResponse actualPokemonReponse = pokeApiService.getPokemonNameOrNumber(Constant.NAME_BULBASAUR);

        assertEquals(pokemonResponse.getName(), actualPokemonReponse.getName());
    }

    @Test
    public void testGetEvolutionsByName() throws JsonProcessingException {
        Species species = new Species();
        species.setName(Constant.NAME_BULBASAUR);
        species.setUrl(Constant.URL_SPECIES_BULBASAUR);

        EvolutionChain evolutionChain = new EvolutionChain();
        evolutionChain.setId(Constant.NUMBER_BULBASAUR);
        evolutionChain.setChain(new ChainLink());

        ResponseEntity<String> responseEntity = new ResponseEntity<>("{}", HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readValue(anyString(), eq(Species.class))).thenReturn(species);

        assertEquals(1, pokeApiService.getEvolutionsByPokemonName(Constant.NAME_BULBASAUR).size());
    }

    @Test
    public void testGetSpecieByName() throws JsonProcessingException {
        Species expectedSpecie = new Species();
        expectedSpecie.setName(Constant.NAME_BULBASAUR);

        ResponseEntity<String> responseEntity = new ResponseEntity<>("{}", HttpStatus.OK);

        when(restTemplate.getForEntity(
                eq("http://localhost:8083/APIs/pokedex/pokemon-specie/bulbasaur"),
                eq(String.class)))
                .thenReturn(responseEntity);

        when(objectMapper.readValue(
                anyString(),
                eq(Species.class)))
                .thenReturn(expectedSpecie);

        Species actualSpecie = pokeApiService.getSpecieByName(Constant.NAME_BULBASAUR);

        assertEquals(expectedSpecie.getName(), actualSpecie.getName());
    }

    @Test
    public void testGetEvolutionChainByUrl() throws JsonProcessingException {
        EvolutionChain expectedChain = new EvolutionChain();
        expectedChain.setId(Constant.NUMBER_BULBASAUR);
        expectedChain.setChain(new ChainLink());

        ResponseEntity<String> responseEntity = new ResponseEntity<>("{}", HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);
        when(objectMapper.readValue(anyString(), eq(EvolutionChain.class))).thenReturn(expectedChain);

        EvolutionChain actualChain = pokeApiService.getEvolutionChainByUrl("http://localhost:8083/APIs/pokedex/evolution-chain/1");

        assertEquals(expectedChain.getId(), actualChain.getId());
    }
}
