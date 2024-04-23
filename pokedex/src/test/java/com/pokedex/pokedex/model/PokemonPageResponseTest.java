package com.pokedex.pokedex.model;

import com.pokedex.pokedex.config.Constant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokemonPageResponseTest {
    private PokemonPageResponse pokemonPageResponse;
    private List<Pokemon> pokemons;
    private Meta meta;

    @BeforeEach
    public void setUp() {
        // Inicializa os mocks e objetos necessários antes de cada teste
        Pokemon pokemon1 = new Pokemon();
        pokemon1.setName(Constant.NAME_BULBASAUR);
        pokemon1.setType(Constant.TYPE_BULBASAUR);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setName("Charmander");
        pokemon2.setType("fire");

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
