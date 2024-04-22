package com.pokedex.pokedex.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "number")
    private Long number;

    @Column(name = "name")
    private String name;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "type")
    private String type;

//    @Column(name = "evolutions")
//    private List<String> evolutionsList;
}
