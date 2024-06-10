package com.pokedex.pokedex.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pokedex.pokedex.config.Constant;
import org.junit.jupiter.api.Test;

public class TypePokemonTest {
    @Test
    public void testTypePokemon() {
        TypePokemon typePokemon = new TypePokemon();
        typePokemon.setPokemonNumber(Constant.NUMBER_BULBASAUR);
        typePokemon.setType("Grass");

        assertEquals(Constant.NUMBER_BULBASAUR, typePokemon.getPokemonNumber());
        assertEquals("Grass", typePokemon.getType());
    }
}
