package com.pokedex.pokedex.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.repository.EvolutionRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EvolutionDetailTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private EvolutionRepository evolutionRepository;

    @Test
    public void testEvolutionDetails(){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(Constant.NAME_BULBASAUR);
        pokemon.setNumber(Constant.NUMBER_BULBASAUR);
        pokemon.setImageUrl("ImageUrl");
        pokemon.setType(Constant.TYPE_BULBASAUR);
        pokemonRepository.save(pokemon);

        EvolutionDetail evolutionDetail = new EvolutionDetail();
        evolutionDetail.setSelf(pokemon);
        evolutionDetail.setMinLevel(Constant.MIN_LEVEL_BULBASAUR);
        evolutionDetail.setTriggerName(Constant.TRIGGER_NAME_BULBASAUR);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setName("Ivysaur");
        pokemon2.setType(Constant.TYPE_BULBASAUR);

        evolutionDetail.setEvolution(pokemon2);

        evolutionRepository.save(evolutionDetail);

        List<EvolutionDetail> savedEvolution = evolutionRepository.findBySelf_Number(evolutionDetail.getId());

        assertNotNull(savedEvolution);
        assertEquals(Constant.NAME_BULBASAUR, savedEvolution.get(0).getSelf().getName());
        assertEquals(Constant.MIN_LEVEL_BULBASAUR, savedEvolution.get(0).getMinLevel());
        assertEquals(pokemon2.getName(), savedEvolution.get(0).getEvolution().getName());
        assertTrue(savedEvolution.get(0).getEvolution().getType().containsAll(Constant.TYPE_BULBASAUR));
    }

}
