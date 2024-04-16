package com.pokedex.pokedex.model;

import lombok.Data;

import java.util.List;

@Data
public class ChainPokemon {
    private PokemonSpecie pokemonSpecie; //representa a espécia atual na cadeia de evolução
    private List<ChainPokemon> evolves_to; //lista de pokemons para os quais ele pode evoluir
}
