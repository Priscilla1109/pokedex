package com.pokedex.pokedex.model;

import lombok.Data;

import java.util.List;

@Data
public class PokemonPageResponse {
    private List<Pokemon> pokemons;
    private Meta meta;
}
