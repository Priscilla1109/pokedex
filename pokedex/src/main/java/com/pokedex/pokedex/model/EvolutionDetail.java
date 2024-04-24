package com.pokedex.pokedex.model;

import lombok.Data;

@Data
public class EvolutionDetail { //detalhes sobre a evolução
    private int minLevel;
    private String triggerName;
    private String itemName;
}