package com.pokedex.pokedex.model;

import lombok.Data;

import java.util.List;

@Data
    public class ChainLink { //representa um elo da cadeia de evolução
    private Species species;
    private List<EvolutionDetail> evolutionDetails;
    private List<ChainLink> evolvesTo;
}