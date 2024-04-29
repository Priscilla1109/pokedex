package com.pokedex.pokedex.model;

import lombok.AllArgsConstructor;
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
    private int number;

    @Column(name = "minLevel")
    private int minLevel;

    @Column(name = "triggerName")
    private String triggerName;

    @Column(name = "itemName")
    private String itemName;

    @ManyToOne(fetch = FetchType.LAZY) //relacionamento com a tabela Pokemon
    @JoinColumn(name = "pokemon_number", referencedColumnName = "number")
    private Pokemon pokemon;

    public EvolutionDetail(int pokemonNumber, String itemName, int minLevel, String triggerName) {
    }
}