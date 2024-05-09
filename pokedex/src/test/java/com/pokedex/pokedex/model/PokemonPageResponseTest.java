package com.pokedex.pokedex.model;

import com.pokedex.pokedex.config.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokemonPageResponseTest {
    private PokemonPageResponse pokemonPageResponse;
    private List<PokemonResponse> pokemons;
    private Meta meta;

    @BeforeEach
    public void setUp() {
        // Inicializa os mocks e objetos necessários antes de cada teste
        PokemonResponse pokemon1 = new PokemonResponse();
        pokemon1.setName(Constant.NAME_BULBASAUR);
        pokemon1.setType(Constant.TYPE_BULBASAUR);

        PokemonResponse pokemon2 = new PokemonResponse();
        pokemon2.setName("Charmander");
        pokemon2.setType(Arrays.asList("Fire"));

        pokemons = Arrays.asList(pokemon1, pokemon2);

        meta = new Meta(2, 1, 2, 2);

        pokemonPageResponse = new PokemonPageResponse();
        pokemonPageResponse.setPokemons(pokemons);
        pokemonPageResponse.setMeta(meta);
    }

    @Test
    public void testGetPokemons() {
        assertEquals(pokemons, pokemonPageResponse.getPokemons());
    }

    @Test
    public void testGetMeta() {
        assertEquals(meta, pokemonPageResponse.getMeta());
    }

}
