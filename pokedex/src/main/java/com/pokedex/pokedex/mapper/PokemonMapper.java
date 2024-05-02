package com.pokedex.pokedex.mapper;

import com.pokedex.pokedex.model.*;

import java.util.ArrayList;
import java.util.List;

public class PokemonMapper {

    public static Pokemon toDomain(PokemonResquest pokemonResquest){
        Pokemon pokemon = new Pokemon();
        pokemon.setNumber(pokemonResquest.getNumber());
        pokemon.setName(pokemonResquest.getName());
        pokemon.setType(pokemonResquest.getType());
        pokemon.setImageUrl(pokemonResquest.getImageUrl());

        List<EvolutionPokemon> evolutions = new ArrayList<>();
        if (pokemonResquest.getEvolutions() != null){
            for (EvolutionPokemon evolutionPokemon : pokemonResquest.getEvolutions()){
                EvolutionPokemon evolution = new EvolutionPokemon();
                evolution.setPokemon(pokemon);
                evolution.setNumber(evolutionPokemon.getNumber());
                evolutions.add(evolution);
            }
        }
        pokemon.setEvolutions(evolutions);

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
