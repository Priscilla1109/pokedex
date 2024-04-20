package com.pokedex.pokedex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//Classe que contém as informações da espécie do pokemon
public class PokemonSpecie {
    private String name;
    private String url;
    private EvolutionChain evolutionChain;
}
