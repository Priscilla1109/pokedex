package com.pokedex.pokedex.mapper;

import com.pokedex.pokedex.model.*;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.List;

public class PokemonMapper {

    public static List<EvolutionDetail> toDomain(PokemonResponse pokemonResponse){
        Pokemon pokemonSelf = toPokemon(pokemonResponse);

        return Optional.ofNullable(pokemonResponse.getEvolutions())
            .orElse(Collections.emptyList())
            .stream()
            .map(evolution -> toEvolutionDetail(evolution, pokemonSelf))
            .collect(Collectors.toList());
    }

    private static EvolutionDetail toEvolutionDetail(PokemonResponse evolution, Pokemon pokemonSelf) {
        var evolutionDetail = new EvolutionDetail();
        evolutionDetail.setSelf(pokemonSelf);
        evolutionDetail.setEvolution(toPokemon(evolution));
        return evolutionDetail;
    }

    public static Pokemon toPokemon(PokemonResponse pokemonResponse) {
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

    public static List<PokemonResponse> toResponseList(List<EvolutionDetail> evolutionDetails) {
        return evolutionDetails.stream()
                .map(evolutionDetail -> toResponse(evolutionDetail.getEvolution()))
                .collect(Collectors.toList());
    }
}
