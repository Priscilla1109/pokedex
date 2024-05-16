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
        pokemon.setImageUrl(Constant.IMAGE_URL_BULBASAUR);
        pokemon.setType(Constant.TYPE_BULBASAUR);

        assertEquals(Constant.NAME_BULBASAUR, pokemon.getName());
        assertEquals(Constant.NUMBER_BULBASAUR, pokemon.getNumber());
        assertEquals(Constant.IMAGE_URL_BULBASAUR, pokemon.getImageUrl());
        assertEquals(Constant.TYPE_BULBASAUR, pokemon.getType());
    }

    @Test
    public void testPokemonConstructor(){
        Pokemon pokemon = new Pokemon(Constant.NUMBER_BULBASAUR, Constant.NAME_BULBASAUR, Constant.IMAGE_URL_BULBASAUR, Constant.TYPE_BULBASAUR);

        assertEquals(Constant.NAME_BULBASAUR, pokemon.getName());
        assertEquals(Constant.NUMBER_BULBASAUR, pokemon.getNumber());
        assertEquals(Constant.IMAGE_URL_BULBASAUR, pokemon.getImageUrl());
        assertEquals(Constant.TYPE_BULBASAUR, pokemon.getType());
    }
}
