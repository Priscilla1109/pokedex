package com.pokedex.pokedex.advice;


import com.pokedex.pokedex.exception.BadRequestException;
import com.pokedex.pokedex.exception.InternalServerErrorException;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class PokemonHandlerExceptionTest {
    @InjectMocks
    PokemonHandlerException pokemonHandlerException;

    @Test
    public void testPokemonNotFoundException() throws Exception {
        ResponseEntity<String> responseEntity = pokemonHandlerException.pokemonNotFoundException(new PokemonNotFoundException("Pokemon not found!"));

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Pokemon not found: null", responseEntity.getBody());
    }

    @Test
    public void testBadRequestException() throws Exception {
        ResponseEntity<String> responseEntity = pokemonHandlerException.badResquestException(new BadRequestException("Bad Request. Please check your request parameters"));

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Bad Request. Please check your request parameters", responseEntity.getBody());
    }

    @Test
    public void testInternalServerErrorException() throws Exception {
        ResponseEntity<String> responseEntity = pokemonHandlerException.internalServerErrorException(new InternalServerErrorException("Internal server error. Please try again later"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Internal server error. Please try again later", responseEntity.getBody());
    }
}
