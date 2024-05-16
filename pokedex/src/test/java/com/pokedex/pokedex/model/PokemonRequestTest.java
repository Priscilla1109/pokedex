package com.pokedex.pokedex.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pokedex.pokedex.config.Constant;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PokemonRequestTest {
    @Test
    public void testPokemonRequest(){
        PokemonResquest pokemonResquest = new PokemonResquest();
        pokemonResquest.setNumber(Constant.NUMBER_BULBASAUR);
        pokemonResquest.setName(Constant.NAME_BULBASAUR);
        pokemonResquest.setType(Constant.TYPE_BULBASAUR);
        pokemonResquest.setImageUrl(Constant.IMAGE_URL_BULBASAUR);
        pokemonResquest.setEvolutions(new ArrayList<>());


        assertEquals(Constant.NUMBER_BULBASAUR, pokemonResquest.getNumber());
        assertEquals(Constant.NAME_BULBASAUR, pokemonResquest.getName());
        assertEquals(Constant.TYPE_BULBASAUR, pokemonResquest.getType());
        assertEquals(Constant.IMAGE_URL_BULBASAUR, pokemonResquest.getImageUrl());
        assertEquals(new ArrayList<>(), pokemonResquest.getEvolutions());
    }
}
