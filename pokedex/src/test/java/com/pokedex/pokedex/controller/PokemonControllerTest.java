package com.pokedex.pokedex.controller;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.service.PokeApiService;
import com.pokedex.pokedex.service.PokemonService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PokemonController.class)
public class PokemonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokeApiService pokeApiService;

    @MockBean
    private PokemonService pokemonService;

    @Test
    public void testGetPokemonNameOrNumber_Success() throws Exception {
        PokemonResponse pokemonResponse = new PokemonResponse();

        when(pokeApiService.getPokemonNameOrNumber(Constant.NAME_BULBASAUR)).thenReturn(pokemonResponse);

        mockMvc.perform(get("/APIs/pokedex/pokemon/bulbasaur"));
    }

    @Test
    public void testAddNewPokemon_Success() throws Throwable {
        PokemonResponse pokemonResponse = new PokemonResponse();

        when(pokeApiService.getPokemonNameOrNumber(Constant.NAME_BULBASAUR)).thenReturn(pokemonResponse);
        when(pokemonService.addNewPokemon(Constant.NAME_BULBASAUR)).thenReturn(Collections.emptyList());

        mockMvc.perform(post("/APIs/pokedex/add/bulbasaur"))
            .andExpect(status().isOk());
    }

    @Test
    public void testListPokemons_Success() throws Exception {
        PokemonPageResponse pokemonPageResponse = new PokemonPageResponse(
            Collections.emptyList(), new Meta(0,10,0,0)
        );

        when(pokemonService.listPokemons(0,10)).thenReturn(pokemonPageResponse);

        mockMvc.perform(get("/APIs/pokedex/pokemons"))
            .andExpect(status().isOk());
    }

    @Test
    public void testDeletePokemon_Success() throws Exception {
        mockMvc.perform(delete("/APIs/pokedex/pokemon/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void testGetEvolutionsByPokemonNumber_Success() throws Exception {
        PokemonResponse evolutionDetails = new PokemonResponse();

        when(pokemonService.getEvolutionsByPokemonNumber(Constant.NUMBER_BULBASAUR)).thenReturn(evolutionDetails);

        mockMvc.perform(get("/APIs/pokedex/evolutions/1"))
            .andExpect(status().isOk());
    }
}
