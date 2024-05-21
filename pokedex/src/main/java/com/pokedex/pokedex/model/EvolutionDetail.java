package com.pokedex.pokedex.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EvolutionDetail { //detalhes sobre a evolução
    private Long id;
    private Pokemon self;
    private int minLevel;
    private String triggerName;
    private Pokemon evolution;
}