package com.pokedex.pokedex.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.model.PokemonResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class PokeApiServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokeApiService pokeApiService;
    @Test
    public void testGetPokemonNameOrNumber() throws Exception{
        PokemonResponse expectPokemonResponse = new PokemonResponse();
        expectPokemonResponse.setName(Constant.NAME_BULBASAUR);

        ResponseEntity<PokemonResponse> successResponse = new ResponseEntity<>(
            expectPokemonResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(PokemonResponse.class))).thenReturn(successResponse);

        PokemonResponse actualPokemon = pokeApiService.getPokemonNameOrNumber(Constant.NAME_BULBASAUR);

        assertEquals(Constant.NAME_BULBASAUR, actualPokemon.getName());
    }

    @Test
    public void testGetPokemonByNameOrNumber_NotFound(){
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "Pokemon not found with name or number: ");


        when(restTemplate.getForEntity("http://localhost:8083/api-pokedex/v2/pokemon/invalid", PokemonResponse.class))
            .thenThrow(exception);

        PokemonNotFoundException pokemonNotFoundException = assertThrows(PokemonNotFoundException.class, () -> {
            pokeApiService.getPokemonNameOrNumber("invalid");
        });

        assertEquals("Pokemon not found with name or number: invalid", pokemonNotFoundException.getMessage());
    }
}
