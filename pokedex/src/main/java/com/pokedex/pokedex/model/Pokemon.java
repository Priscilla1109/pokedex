package com.pokedex.pokedex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pokemons")
public class Pokemon {
    @Id
    @Column(name = "number")
    private Long number;

    @Column(name = "name")
    private String name;

    @Column(name = "imageUrl")
    private String imageUrl;

    @ElementCollection
    private List<String> type;

    @OneToMany(mappedBy = "self", cascade = CascadeType.ALL)
    private List<EvolutionDetail> evolutionDetails;

    @OneToMany(mappedBy = "self", cascade = CascadeType.ALL)
    private List<EvolutionPokemon> evolutions;
}
