package com.pokedex.pokedex.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class EvolutionDetail { //detalhes sobre a evolução
    private Long id;
    private Pokemon self;
    private int minLevel;
    private String triggerName;
    private Pokemon evolution;
}