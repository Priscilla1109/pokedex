package com.pokedex.pokedex.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;


//Classe que contém os campos necessários para uma ação com relação ao Pokemon
@Data
public class PokemonResquest {
    @NotBlank(message = "invalid number")
    private Long number;

    @NotBlank(message = "invalid name")
    private String name;

    @NotBlank(message = "invalid image URL")
    private String imageUrl;

    @NotBlank(message = "invalid description")
    private String description;

    @NotBlank(message = "invalid evolutions list")
    private List<String> evolutions;
}
