package com.pokedex.pokedex.model;

import lombok.Data;

import java.util.List;

@Data
public class EvolutionApiResponse {
    private List<EvolutionDetail> evolutions;
}

