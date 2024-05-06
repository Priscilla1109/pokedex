package com.pokedex.pokedex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "evolutions")
@AllArgsConstructor
@NoArgsConstructor
@Data
//Representa as evoluçoes que o pokemon pode ter
public class EvolutionPokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id")
    private Pokemon self;

    @Column(name = "evolved_number")
    private Long number;
}
