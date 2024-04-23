package com.pokedex.pokedex.model;

import com.pokedex.pokedex.config.Constant;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PokemonRequestTest {
    @Test
    public void testPokemonRequest(){
        PokemonResquest pokemonResquest = new PokemonResquest();
        pokemonResquest.setNumber(Constant.NUMBER_BULBASAUR);
        pokemonResquest.setName(Constant.NAME_BULBASAUR);
        pokemonResquest.setType(Constant.TYPE_BULBASAUR);
        pokemonResquest.setImageUrl(Constant.IMAGE_URL_BULBASAUR);

        List<EvolutionChain.ChainLink.EvolutionDetail> listEvolutionDetails = new ArrayList<>();
        EvolutionChain.ChainLink.EvolutionDetail evolutionDetails = new EvolutionChain.ChainLink.EvolutionDetail();

        listEvolutionDetails.add(evolutionDetails);
        evolutionDetails.setMinLevel(Constant.MIN_LEVEL_BULBASAUR);
        evolutionDetails.setItemName(Constant.ITEM_NAME_BULBASAUR);
        evolutionDetails.setTriggerName(Constant.TRIGGER_NAME_BULBASAUR);
        pokemonResquest.setEvolutions(listEvolutionDetails);

        assertEquals(Constant.NUMBER_BULBASAUR, pokemonResquest.getNumber());
        assertEquals(Constant.NAME_BULBASAUR, pokemonResquest.getName());
        assertEquals(Constant.TYPE_BULBASAUR, pokemonResquest.getType());
        assertEquals(Constant.IMAGE_URL_BULBASAUR, pokemonResquest.getImageUrl());
        assertEquals(Constant.MIN_LEVEL_BULBASAUR, pokemonResquest.getEvolutions().get(0).getMinLevel());
        assertEquals(Constant.ITEM_NAME_BULBASAUR, pokemonResquest.getEvolutions().get(0).getItemName());
        assertEquals(Constant.TRIGGER_NAME_BULBASAUR, pokemonResquest.getEvolutions().get(0).getTriggerName());
    }
}
