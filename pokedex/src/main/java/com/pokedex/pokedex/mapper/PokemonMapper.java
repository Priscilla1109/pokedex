package com.pokedex.pokedex.mapper;

import com.pokedex.pokedex.model.*;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PokemonMapper {

    public static EvolutionDetail toDomain(PokemonResponse pokemonResponse){
        Pokemon pokemonSelf = new Pokemon();
        pokemonSelf.setNumber(pokemonResponse.getNumber());
        pokemonSelf.setName(pokemonResponse.getName());
        pokemonSelf.setType(pokemonResponse.getType());
        pokemonSelf.setImageUrl(pokemonResponse.getImageUrl());

        EvolutionDetail evolution = new EvolutionDetail();
        evolution.setSelf(pokemonSelf);
        evolution.setEvolution(pokemonResponse.getEvolutions()
            .stream()
            .map(PokemonMapper::toPokemon)
            .collect(Collectors.toList()));

        return evolution;
    }

    private static Pokemon toPokemon(PokemonResponse pokemonResponse) {
        Pokemon pokemon = new Pokemon();
        pokemon.setNumber(pokemonResponse.getNumber());
        pokemon.setName(pokemonResponse.getName());
        pokemon.setImageUrl(pokemonResponse.getImageUrl());
        pokemon.setType(pokemonResponse.getType());
        return pokemon;
    }

    public static PokemonResponse toResponse(Pokemon pokemon) {
        PokemonResponse response = new PokemonResponse();
        response.setNumber(pokemon.getNumber());
        response.setName(pokemon.getName());
        response.setType(pokemon.getType());
        response.setImageUrl(pokemon.getImageUrl());

        return response;
    }
}
