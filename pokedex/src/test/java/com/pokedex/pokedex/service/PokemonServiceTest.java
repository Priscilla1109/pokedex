package com.pokedex.pokedex.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

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
        pokemon.setName(Constant.NAME_BULBASAUR);

        ResponseEntity<Pokemon> responseEntity = new ResponseEntity<>(pokemon, HttpStatus.OK);

        //Configurar o mock
        when(restTemplate.getForEntity(anyString(), eq(Pokemon.class))).thenReturn(responseEntity);
        when(pokemonService.addNewPokemon(new Pokemon())).thenReturn(pokemon);
    }
}
