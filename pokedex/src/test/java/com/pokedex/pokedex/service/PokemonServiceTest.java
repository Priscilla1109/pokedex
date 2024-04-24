package com.pokedex.pokedex.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonResponse;
import org.hibernate.validator.constraints.br.CPF;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PokemonService pokemonService;
    @Test
    public void testAddNewPokemon(){
        //Mockar a resposta da API
        Pokemon pokemon = new Pokemon();
        pokemon.setNumber(Constant.NUMBER_BULBASAUR);
        pokemon.setName(Constant.NAME_BULBASAUR);
        pokemon.setType(new ArrayList<>());
        pokemon.setImageUrl(Constant.IMAGE_URL_BULBASAUR);

        ResponseEntity<Pokemon> responseEntity = new ResponseEntity<>(pokemon, HttpStatus.OK);

        //Configurar o mock
        when(restTemplate.getForEntity(anyString(), eq(Pokemon.class))).thenReturn(responseEntity);
        when(pokemonService.addNewPokemon(new Pokemon())).thenReturn(pokemon);
    }

    @Test
    public void testDeletePokemon(){
        Long pokemonNumber = Constant.NUMBER_BULBASAUR;
        String deleteUrl = "https://localhost:8083/APIs/pokedex/" + pokemonNumber;

        restTemplate.delete(deleteUrl);

        verify(restTemplate, times(1)).delete(deleteUrl);
    }
}
