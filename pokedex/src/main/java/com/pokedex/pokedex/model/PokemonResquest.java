package com.pokedex.pokedex.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


//Classe que contém os campos necessários do payload dos pokemons
@Data
public class PokemonResquest {
    @NotNull(message = "invalid number")
    private Long number;

    @NotBlank(message = "invalid name")
    private String name;

    @NotBlank(message = "invalid image URL")
    private String imageUrl;

    @NotNull(message = "invalid type")
    private List<String> type;

    @JsonProperty("evolutions")
    @NotNull(message = "invalid evolutions list")
    private List<PokemonResquest> evolutions;

    @JsonProperty("evolutionDetails")
    @NotNull(message = "invalid evolutionDetails list")
    private List<EvolutionDetail> evolutionDetails = new ArrayList<>();
}
