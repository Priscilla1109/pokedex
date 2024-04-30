package com.pokedex.pokedex.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "evolution_detaill")
@NoArgsConstructor
public class EvolutionDetail { //detalhes sobre a evolução
    @Id
    @Column(name = "number")
    private Long number;

    @Column(name = "minLevel")
    private int minLevel;

    @Column(name = "triggerName")
    private String triggerName;

    @ManyToOne(fetch = FetchType.LAZY) //relacionamento com a tabela Pokemon
    @JoinColumn(name = "pokemon_number", referencedColumnName = "number")
    private Pokemon pokemon;

}