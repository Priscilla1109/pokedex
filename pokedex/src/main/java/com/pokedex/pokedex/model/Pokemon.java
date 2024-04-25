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

    @ElementCollection
    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, orphanRemoval = true)
    //cascade = configuração que define operações de persistência (salvar, atualizar, deletar)
    //condição true garante a remoção de detalhes de evolução que não estão relacionados a nenhum pokemon
    private List<EvolutionDetail> evolutions;
}
