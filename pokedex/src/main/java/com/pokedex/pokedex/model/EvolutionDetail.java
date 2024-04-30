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
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "pokemonOriginal")
    @ManyToOne(fetch = FetchType.LAZY) //relacionamento com a tabela Pokemon
    @JoinColumn(name = "pokemon_number", referencedColumnName = "number")
    private Pokemon self;

    @Column(name = "minLevel")
    private int minLevel;

    @Column(name = "triggerName")
    private String triggerName;

    @ManyToOne(fetch = FetchType.LAZY) //relacionamento com a tabela Pokemon
    @JoinColumn(name = "pokemon_number", referencedColumnName = "number")
    @ElementCollection
    private List<Pokemon> evolution;

}