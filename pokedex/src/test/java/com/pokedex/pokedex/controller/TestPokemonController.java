package com.pokedex.pokedex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonResquest;
import com.pokedex.pokedex.service.PokeApiService;
import com.pokedex.pokedex.service.PokemonService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PokemonController.class)
public class TestPokemonController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    //cria uma instancia simulada da classe
    private PokeApiService pokeApiService;

    @Test
    public void testGetPokemonNameOrNumber(){
        PokemonResquest pokemonResquest = new PokemonResquest();
        Pokemon pokemon = PokemonMapper.toDomain(pokemonResquest);
        pokemon.setName("bulbasaur");

        when(pokeApiService.getPokemonNameOrNumber(pokemon.getName())).thenReturn(pokemonResquest);

        mockMvc.perform(get("/APIs/pokedex/pokemon/bulbasaur")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonResquest)))
                //resposta da requisição
                .andExpect(status().isOk())
                //corpo
                .andExpect(jsonPath("$ name", is("bulbasaur"))
        );
    }
}
