package com.pokedex.pokedex.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class EvolutionDetail { //detalhes sobre a evolução
    private int number;
    private int minLevel;
    private String triggerName;
    private String itemName;

    @ManyToOne
    @JoinColumn(name = "pokemon_number")
    private Pokemon pokemon;
}