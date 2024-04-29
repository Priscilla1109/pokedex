package com.pokedex.pokedex.mapper;

import com.pokedex.pokedex.model.EvolutionDetail;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonResponse;
import com.pokedex.pokedex.model.PokemonResquest;

import java.util.ArrayList;
import java.util.List;

public class PokemonMapper {

    public static Pokemon toDomain(PokemonResquest pokemonResquest){
        Pokemon pokemon = new Pokemon();
        pokemon.setNumber(pokemonResquest.getNumber());
        pokemon.setName(pokemonResquest.getName());
        pokemon.setType(pokemonResquest.getType());
        pokemon.setImageUrl(pokemonResquest.getImageUrl());

        List<PokemonResquest> evolutions = new ArrayList<>();
        if (pokemonResquest.getEvolutions() != null){
            for (PokemonResquest evolutionRequest : pokemonResquest.getEvolutions()){
                PokemonResquest evolution = new PokemonResquest();
                evolution.setName(evolutionRequest.getName());
                evolution.setType(evolutionRequest.getType());
                evolutionRequest.setImageUrl(evolutionRequest.getImageUrl());
            }
        }
        pokemon.setEvolutionsList(evolutions);

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
