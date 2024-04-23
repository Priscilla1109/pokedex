package com.pokedex.pokedex.model;

import com.pokedex.pokedex.config.Constant;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EvolutionChainTest {
    //Testes de getters e setters
    @Test
    public void testEvolutionChain(){
        EvolutionChain evolutionChain = new EvolutionChain();

        EvolutionChain.ChainLink chainLink = new EvolutionChain.ChainLink();
        chainLink.setSpecies(new EvolutionChain.ChainLink.Species());
        chainLink.getSpecies().setName(Constant.NAME_BULBASAUR);
        chainLink.getSpecies().setUrl(Constant.URL_SPECIES_BULBASAUR);

        EvolutionChain.ChainLink.EvolutionDetail evolutionDetail = new EvolutionChain.ChainLink.EvolutionDetail();
        evolutionDetail.setMinLevel(Constant.MIN_LEVEL_BULBASAUR);
        evolutionDetail.setTriggerName(Constant.TRIGGER_NAME_BULBASAUR);
        evolutionDetail.setItemName(Constant.ITEM_NAME_BULBASAUR);

        chainLink.setEvolutionDetails(List.of(evolutionDetail));
        chainLink.setEvolvesTo(new ArrayList<>());

        evolutionChain.setId(Constant.NUMBER_BULBASAUR);
        evolutionChain.setChain(chainLink);

        assertEquals(1, evolutionChain.getId());
        assertNotNull(evolutionChain.getChain());
        assertEquals(Constant.NAME_BULBASAUR, evolutionChain.getChain().getSpecies().getName());
        assertEquals(Constant.URL_SPECIES_BULBASAUR, evolutionChain.getChain().getSpecies().getUrl());
        assertEquals(1, evolutionChain.getChain().getEvolutionDetails().size());
        assertEquals(Constant.MIN_LEVEL_BULBASAUR, evolutionChain.getChain().getEvolutionDetails().get(0).getMinLevel());
        assertEquals(Constant.TRIGGER_NAME_BULBASAUR, evolutionChain.getChain().getEvolutionDetails().get(0).getTriggerName());
        assertEquals(Constant.ITEM_NAME_BULBASAUR, evolutionChain.getChain().getEvolutionDetails().get(0).getItemName());
        assertTrue(evolutionChain.getChain().getEvolvesTo().isEmpty());
    }
}
