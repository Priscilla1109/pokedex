package com.pokedex.pokedex.model;

import com.pokedex.pokedex.config.Constant;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokemonResponseTest {
    @Test
    public void testPokemonResponse(){
        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setNumber(Constant.NUMBER_BULBASAUR);
        pokemonResponse.setName(Constant.NAME_BULBASAUR);
        pokemonResponse.setType(Constant.TYPE_BULBASAUR);
        pokemonResponse.setImageUrl(Constant.IMAGE_URL_BULBASAUR);
        pokemonResponse.setEvolutions(Constant.EVOLUTION_BULBASAUR);

        assertEquals(Constant.NUMBER_BULBASAUR, pokemonResponse.getNumber());
        assertEquals(Constant.NAME_BULBASAUR, pokemonResponse.getName());
        assertEquals(Constant.TYPE_BULBASAUR, pokemonResponse.getType());
        assertEquals(Constant.IMAGE_URL_BULBASAUR, pokemonResponse.getImageUrl());
        assertEquals(Constant.EVOLUTION_BULBASAUR, pokemonResponse.getEvolutions());
    }

    @Test
    public void testPokemonResponseConstructor(){
        PokemonResponse pokemonResponse = new PokemonResponse(
            Constant.NUMBER_BULBASAUR,
            Constant.NAME_BULBASAUR,
            Constant.TYPE_BULBASAUR,
            Constant.IMAGE_URL_BULBASAUR,
            Constant.EVOLUTION_BULBASAUR);

        assertEquals(Constant.NUMBER_BULBASAUR, pokemonResponse.getNumber());
        assertEquals(Constant.NAME_BULBASAUR, pokemonResponse.getName());
        assertEquals(Constant.TYPE_BULBASAUR, pokemonResponse.getType());
        assertEquals(Constant.IMAGE_URL_BULBASAUR, pokemonResponse.getImageUrl());
        assertEquals(Constant.EVOLUTION_BULBASAUR, pokemonResponse.getEvolutions());
    }
}
