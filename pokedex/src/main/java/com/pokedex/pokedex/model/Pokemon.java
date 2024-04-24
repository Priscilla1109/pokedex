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
    private List<String> evolutions;
}
