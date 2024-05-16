package com.pokedex.pokedex.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokemonPageResponseTest {

    @Test
    public void testPokmonPageResponseConstructor(){
        PokemonPageResponse pokemonPageResponse = new PokemonPageResponse(
            new ArrayList<>(),
            new Meta(2, 1, 2, 2));

        assertEquals(new ArrayList<>(), pokemonPageResponse.getPokemons());
        assertEquals(new Meta(2, 1, 2, 2), pokemonPageResponse.getMeta());
    }

    @Test
    public void testPokmonPageResponseNoArgsConstructor(){
        PokemonPageResponse pokemonPageResponse = new PokemonPageResponse();
        List<PokemonResponse> pokemonResponses = new ArrayList<>();
        Meta meta = new Meta(0,0,10,0);

        assertEquals(new PokemonPageResponse(), pokemonPageResponse);
        assertEquals(new ArrayList<>(), pokemonResponses);
        assertEquals(new Meta(0,0,10,0), meta);
    }
}
