package com.pokedex.pokedex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.service.PokeApiService;
import com.pokedex.pokedex.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @MockBean
    private PokemonService pokemonService;

    @Test
    public void testGetPokemonNameOrNumber() throws Exception {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(Constant.NAME_BULBASAUR);
        pokemon.setNumber(Constant.NUMBER_BULBASAUR);
        pokemon.setImageUrl(Constant.URL_BULBASAUR);
        pokemon.setDescription(Constant.TYPE_BULBASAUR);

        PokemonResponse pokemonResponse = PokemonMapper.toResponse(pokemon);

        when(pokeApiService.getPokemonNameOrNumber(pokemon.getName())).thenReturn(pokemonResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/APIs/pokedex/pokemon/bulbasaur")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(pokemonResponse.getNumber()))
                .andExpect(jsonPath("$.name").value(pokemonResponse.getName()));
    }

    @Test
    public void testAddNewPokemon() throws Exception {
        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setNumber(Constant.NUMBER_BULBASAUR);
        pokemonResponse.setName(Constant.NAME_BULBASAUR);
        pokemonResponse.setType(Constant.TYPE_BULBASAUR);
        pokemonResponse.setImageUrl(Constant.URL_BULBASAUR);

        when(pokemonService.addNewPokemon(any(PokemonResquest.class))).thenReturn(pokemonResponse);

        mockMvc.perform(post("/APIs/pokedex/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").value(pokemonResponse.getNumber()))
                .andExpect(jsonPath("$.name").value(pokemonResponse.getName()))
                .andExpect(jsonPath("$.description").value(pokemonResponse.getType()))
                .andExpect(jsonPath("$.imageUrl").value(pokemonResponse.getImageUrl()));
    }

    @Test
    public void testListPokemon() throws Exception{
        PokemonResquest pokemonResquest = new PokemonResquest();
        Pokemon pokemon = PokemonMapper.toDomain(pokemonResquest);
        pokemon.setName(Constant.NAME_BULBASAUR);
        pokemon.setNumber(Constant.NUMBER_BULBASAUR);
        pokemon.setDescription(Constant.TYPE_BULBASAUR);
        pokemon.setImageUrl(Constant.URL_BULBASAUR);

        PokemonResquest pokemonResquest2 = new PokemonResquest();
        Pokemon pokemon2 = PokemonMapper.toDomain(pokemonResquest2);
        pokemon2.setName("pikachu");
        pokemon2.setNumber(20L);
        pokemon2.setDescription("description");
        pokemon2.setImageUrl("url");

        List<Pokemon> pokemons = Arrays.asList(pokemon, pokemon2);
        Page<Pokemon> pokemonPageResponses = new PageImpl<>(pokemons);

        when(pokemonService.listPokemons(0,10)).thenReturn(pokemonPageResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/APIs/pokedex/pokemons")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".pokemons.length()").value(pokemonPageResponses.getTotalElements()))
                //body pokemon
                .andExpect(jsonPath("$.pokemons[0].number").value(pokemon.getNumber()))
                .andExpect(jsonPath("$.pokemons[0].name").value(pokemon.getName()))
                .andExpect(jsonPath("$.pokemons[0].description").value(pokemon.getDescription()))
                .andExpect(jsonPath("$.pokemons[0].imageUrl").value(pokemon.getImageUrl()))
                //body pokemon2
                .andExpect(jsonPath("$.pokemons[1].number").value(pokemon2.getNumber()))
                .andExpect(jsonPath("$.pokemons[1].name").value(pokemon2.getName()))
                .andExpect(jsonPath("$.pokemons[1].description").value(pokemon2.getDescription()))
                .andExpect(jsonPath("$.pokemons[1].imageUrl").value(pokemon2.getImageUrl()));
    }

    @Test
    public void testDeletePokemon() throws Exception{
        PokemonResquest pokemonResquest = new PokemonResquest();
        pokemonResquest.setNumber(Constant.NUMBER_BULBASAUR);
        pokemonResquest.setName(Constant.NAME_BULBASAUR);
        pokemonResquest.setType(Constant.TYPE_BULBASAUR);
        pokemonResquest.setImageUrl(Constant.URL_BULBASAUR);
        Pokemon pokemon = PokemonMapper.toDomain(pokemonResquest);

        when(pokemonService.deletePokemon(pokemon.getNumber())).thenReturn(true);

        mockMvc.perform(delete("/APIs/pokedex/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemon)))
                .andExpect(status().isOk());

        verify(pokemonService, times(1)).deletePokemon(pokemon.getNumber());
    }

    @Test
    public void tesGetSpeciesByName() throws Exception {
        PokemonSpecie pokemonSpecie = new PokemonSpecie();
        pokemonSpecie.setName(Constant.NAME_BULBASAUR);

        when(pokeApiService.getSpecieByName(anyString())).thenReturn(pokemonSpecie);

        mockMvc.perform(get("/APIs/pokedex/species/bulbasaur"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(pokemonSpecie.getName()));
    }

    @Test
    public void testGetEvolutionChainByUrl() throws Exception{
        String url = "htrps://pokeapi.co/api/v2/evolution-chain/1";
        String mockResponseBody = Constant.EVOLUTION_BULBASAUR;

        EvolutionChain expectedEvolutionChain = new EvolutionChain();

        when(pokeApiService.getEvolutionChainByUrl(url)).thenReturn(expectedEvolutionChain);

        mockMvc.perform(get("/APIs/pokedex/evolution-chain")
                .param(url, "url")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mockResponseBody));
    }
}
