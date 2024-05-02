package com.pokedex.pokedex.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "evolution_detaill")
@NoArgsConstructor
public class EvolutionDetail { //detalhes sobre a evolução
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //relacionamento com a tabela Pokemon
    private Pokemon self;

    private int minLevel;

    private String triggerName;

    @ManyToOne(fetch = FetchType.LAZY) //relacionamento com a tabela Pokemon
    @ElementCollection
    private List<Pokemon> evolution;

}