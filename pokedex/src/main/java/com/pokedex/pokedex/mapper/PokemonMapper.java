package com.pokedex.pokedex.mapper;

import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonResponse;
import com.pokedex.pokedex.model.PokemonResquest;

public class PokemonMapper {

    public static Pokemon toDomain(PokemonResquest pokemonResquest){
        Pokemon pokemon = new Pokemon();
        pokemon.setNumber(pokemonResquest.getNumber());
        pokemon.setName(pokemonResquest.getName());
        pokemon.setType(pokemonResquest.getType());
        pokemon.setImageUrl(pokemonResquest.getImageUrl());
//        pokemon.setEvolutionsList(pokemonResquest.getEvolutions());

        return pokemon;
    }

    public static PokemonResponse toResponse(Pokemon pokemon) {
        PokemonResponse response = new PokemonResponse();
        response.setNumber(pokemon.getNumber());
        response.setName(pokemon.getName());
        response.setType(pokemon.getType());
        response.setImageUrl(pokemon.getImageUrl());
//        response.setEvolutions(pokemon.getEvolutionsList());

        return response;
    }
}
