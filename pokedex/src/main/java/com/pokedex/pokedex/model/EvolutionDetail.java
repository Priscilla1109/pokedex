package com.pokedex.pokedex.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "evolution_details")
@NoArgsConstructor
public class EvolutionDetail { //detalhes sobre a evolução
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id")
    private Pokemon self;

    private int minLevel;

    private String triggerName;

    @ElementCollection
    private List<Pokemon> evolution;
}