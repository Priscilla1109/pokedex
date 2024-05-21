package com.pokedex.pokedex.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pokedex.pokedex.config.Constant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/*
@DataJpaTest
public class EvolutionDetailTest {

    @Autowired
    private EvolutionRepository evolutionRepository;
    @Autowired
    private PokemonRepository pokemonRepository;


    @Test
    public void testEvolutionDetailPersistence(){
        Pokemon selfPokemon = new Pokemon();
        selfPokemon.setName(Constant.NAME_BULBASAUR);
        selfPokemon.setNumber(Constant.NUMBER_BULBASAUR);
        selfPokemon.setType(Constant.TYPE_BULBASAUR);
        selfPokemon.setImageUrl(Constant.IMAGE_URL_BULBASAUR);
        pokemonRepository.save(selfPokemon);

        Pokemon evolutionPokemon = new Pokemon();
        evolutionPokemon.setName(Constant.NAME_IVYSAUR);
        evolutionPokemon.setNumber(Constant.NUMBER_IVYSAUR);
        evolutionPokemon.setType(Constant.TYPE_BULBASAUR);
        pokemonRepository.save(evolutionPokemon);

        EvolutionDetail evolutionDetail = new EvolutionDetail();
        evolutionDetail.setSelf(selfPokemon);
        evolutionDetail.setEvolution(evolutionPokemon);
        evolutionDetail.setMinLevel(Constant.MIN_LEVEL_BULBASAUR);
        evolutionDetail.setTriggerName(Constant.TRIGGER_NAME_BULBASAUR);

        evolutionRepository.save(evolutionDetail);

        Optional<EvolutionDetail> savedEvolutionDetail = evolutionRepository.findById(evolutionDetail.getId());

        assertTrue(savedEvolutionDetail.isPresent());
        assertEquals(selfPokemon.getName(), savedEvolutionDetail.get().getSelf().getName());
        assertEquals(evolutionPokemon.getName(), savedEvolutionDetail.get().getEvolution().getName());
        assertEquals(evolutionDetail.getMinLevel(), savedEvolutionDetail.get().getMinLevel());
        assertEquals(evolutionDetail.getTriggerName(), savedEvolutionDetail.get().getTriggerName());
    }

}
*/
