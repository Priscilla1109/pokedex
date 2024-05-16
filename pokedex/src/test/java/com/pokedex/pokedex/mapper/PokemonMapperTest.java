package com.pokedex.pokedex.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.model.EvolutionDetail;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PokemonMapperTest {
    @Test
    public void  testToPokemon_Success(){
        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setName(Constant.NAME_BULBASAUR);
        pokemonResponse.setNumber(Constant.NUMBER_BULBASAUR);
        pokemonResponse.setImageUrl(Constant.IMAGE_URL_BULBASAUR);
        pokemonResponse.setType(Constant.TYPE_BULBASAUR);

        Pokemon pokemon = PokemonMapper.toPokemon(pokemonResponse);

        assertEquals(Constant.NUMBER_BULBASAUR, pokemon.getNumber());
        assertEquals(Constant.NAME_BULBASAUR, pokemon.getName());
        assertEquals(Constant.IMAGE_URL_BULBASAUR, pokemon.getImageUrl());
        assertEquals(Constant.TYPE_BULBASAUR, pokemon.getType());
    }

    @Test
    public void testToResponse(){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(Constant.NAME_BULBASAUR);
        pokemon.setNumber(Constant.NUMBER_BULBASAUR);
        pokemon.setImageUrl(Constant.IMAGE_URL_BULBASAUR);
        pokemon.setType(Constant.TYPE_BULBASAUR);

        PokemonResponse pokemonResponse = PokemonMapper.toResponse(pokemon);

        assertEquals(Constant.NUMBER_BULBASAUR, pokemonResponse.getNumber());
        assertEquals(Constant.NAME_BULBASAUR, pokemonResponse.getName());
        assertEquals(Constant.IMAGE_URL_BULBASAUR, pokemonResponse.getImageUrl());
        assertEquals(Constant.TYPE_BULBASAUR, pokemonResponse.getType());
    }

    @Test
    public void testToResponseList(){
        EvolutionDetail evolutionDetail = new EvolutionDetail();
        Pokemon self = new Pokemon();
        self.setNumber(Constant.NUMBER_BULBASAUR);
        self.setName(Constant.NAME_BULBASAUR);
        Pokemon evolution1 = new Pokemon();
        evolution1.setName(Constant.NAME_IVYSAUR);
        evolution1.setNumber(Constant.NUMBER_IVYSAUR);
        evolutionDetail.setEvolution(evolution1);

        EvolutionDetail evolutionDetail2 = new EvolutionDetail();
        Pokemon self2 = new Pokemon();
        self2.setNumber(Constant.NUMBER_IVYSAUR);
        self2.setName(Constant.NAME_IVYSAUR);
        evolutionDetail2.setSelf(self2);
        Pokemon evolution2 = new Pokemon();
        evolution2.setNumber(Constant.NUMBER_VENUSAUR);
        evolution2.setName(Constant.NAME_VENUSAUR);
        evolutionDetail2.setEvolution(evolution2);

        List<EvolutionDetail> evolutionDetails = List.of(evolutionDetail, evolutionDetail2);

        List<PokemonResponse> result = PokemonMapper.toResponseList(evolutionDetails);

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getNumber());
        assertEquals(Constant.NAME_IVYSAUR, result.get(0).getName());
        assertEquals(3, result.get(1).getNumber());
        assertEquals(Constant.NAME_VENUSAUR, result.get(1).getName());
    }

    @Test
    public void testToDomain(){
        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setName(Constant.NAME_BULBASAUR);
        pokemonResponse.setNumber(Constant.NUMBER_BULBASAUR);
        pokemonResponse.setImageUrl(Constant.IMAGE_URL_BULBASAUR);
        pokemonResponse.setType(Constant.TYPE_BULBASAUR);

        PokemonResponse evolution1 = new PokemonResponse();
        evolution1.setName(Constant.NAME_IVYSAUR);
        evolution1.setNumber(Constant.NUMBER_IVYSAUR);

        PokemonResponse evolution2 = new PokemonResponse();
        evolution2.setNumber(Constant.NUMBER_VENUSAUR);
        evolution2.setName(Constant.NAME_VENUSAUR);

        List<PokemonResponse> evolutions = Arrays.asList(evolution1, evolution2);
        pokemonResponse.setEvolutions(evolutions);

        List<EvolutionDetail> result = PokemonMapper.toDomain(pokemonResponse);
        assertEquals(2, result.size());
        assertEquals(Constant.NAME_BULBASAUR, result.get(0).getSelf().getName());
        assertEquals(Constant.NAME_IVYSAUR, result.get(0).getEvolution().getName());
        assertEquals(Constant.NAME_BULBASAUR, result.get(1).getSelf().getName());
        assertEquals(Constant.NAME_VENUSAUR, result.get(1).getEvolution().getName());
    }
}
