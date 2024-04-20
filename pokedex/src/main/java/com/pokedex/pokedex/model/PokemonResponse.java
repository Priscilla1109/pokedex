package com.pokedex.pokedex.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Classe responsável por representar os dados retornados pelo servidor em resposta a uma solicitação
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonResponse {
    private Long number;
    private String name;
    private String type;
    private String imageUrl;
//    private List<String> evolutions;

}
