package com.pokedex.pokedex.model;

import com.pokedex.pokedex.config.Constant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokemonTest {
    @Test
    public void testPokemon(){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(Constant.NAME_BULBASAUR);
        pokemon.setNumber(Constant.NUMBER_BULBASAUR);
        pokemon.setImageUrl("ImageUrl");
        pokemon.setType(Constant.TYPE_BULBASAUR);

        assertEquals(Constant.NAME_BULBASAUR, pokemon.getName());
        assertEquals(Constant.NUMBER_BULBASAUR, pokemon.getNumber());
        assertEquals("ImageUrl", pokemon.getImageUrl());
        assertEquals(Constant.TYPE_BULBASAUR, pokemon.getType());
    }
}
