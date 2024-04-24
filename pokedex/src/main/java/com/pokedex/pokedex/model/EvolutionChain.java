package com.pokedex.pokedex.model;

import lombok.Data;


@Data
//Classe que contém o atributos da cadeia de evolução do Pokemon
public class EvolutionChain {
    private Long id;
    private ChainLink chain;
}
