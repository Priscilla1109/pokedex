package com.pokedex.pokedex.mapper;

import com.pokedex.pokedex.model.*;

import java.util.Collections;
import java.util.stream.Collectors;

import java.util.List;

public class PokemonMapper {

    public static List<EvolutionDetail> toDomain(PokemonResponse pokemonResponse){
        Pokemon pokemonSelf = toPokemon(pokemonResponse);
        //TODO: ajustar retorno colocando if para se evolução for preenchida ou null
        //Verificar se há evoluções disponíveis
        List<PokemonResponse> evolutions = pokemonResponse.getEvolutions();
        if (evolutions == null || evolutions.isEmpty()) {
            // Se não houver evoluções, retornar uma lista vazia
            return Collections.emptyList();
        }
        // Caso contrário, mapear as evoluções
        return evolutions.stream()
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

    public static PokemonResponse toResponse(List<EvolutionDetail> evolutionDetails) {
        PokemonResponse pokemonResponse = new PokemonResponse();

        if (!evolutionDetails.isEmpty()) {
            Pokemon self = evolutionDetails.get(0).getSelf();
            pokemonResponse = toResponse(self);
            pokemonResponse.setEvolutions(evolutionDetails.stream()
                    .map(EvolutionDetail::getEvolution)
                    .map(PokemonMapper::toResponse)
                    .collect(Collectors.toList()));
        }
        return pokemonResponse;
    }
}
