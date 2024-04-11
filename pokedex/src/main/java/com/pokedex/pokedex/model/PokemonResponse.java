package com.pokedex.pokedex.model;

import lombok.Data;

import java.util.List;

//Classe responsável por representar os dados retornados pelo servidor em resposta a uma solicitação
@Data
public class PokemonResponse {
    private Long number;
    private String name;
    private String description;
    private String imageUrl;
    private List<String> evolutions;

    public PokemonResponse(){
        //construtor vazio para definir atributos posteriormente
    }

    public PokemonResponse(Long number, String name, String description, String imageUrl, List<String> evolutionsList) {
        this.number = number;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.evolutions = evolutionsList;
    }
}
