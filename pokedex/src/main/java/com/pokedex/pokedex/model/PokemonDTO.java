package com.pokedex.pokedex.model;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.List;

//Classe responsável por transformar os dados entre a camada Controller e a camada Service ou vice-versa
@Data
public class PokemonDTO {
    private String name;
    private int number;
    private String imageUrl;
    private String description;
    private List<String> evolutions;

    public PokemonDTO(String name, int number, String imageUrl, String description, List<String> evolutions){
        this.name = name;
        this.number = number;
        this.imageUrl = imageUrl;
        this.description = description;
        this.evolutions = evolutions;
    }
}
