package com.pokedex.pokedex.advice;


import com.pokedex.pokedex.exception.BadRequestException;
import com.pokedex.pokedex.exception.InternalServerErrorException;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.model.PokemonResquest;
import com.pokedex.pokedex.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
