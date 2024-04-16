package com.pokedex.pokedex.model;

import lombok.Data;

//Classe que representa os dados da evolução do pokemon
@Data
public class EvolutionPokemon {
    private String speciesName;
    private String evolutionMethod; //método da evolução do pokemon
    private String trigger; //gatilho para a evolução
    private String item; //item necessário para evolução
    private Integer level; //nível necessário para evolução

    @Override
    public String toString(){
        return "EvolutionPokemon{" + "speciesName = " + speciesName + '\''
                + ", evolutionMethod = " + evolutionMethod + '\''
                + ", trigger = " + evolutionMethod + '\''
                + ". item = " + item + '\''
                + ", level = " + level + '\''
                + "}";
    }
}
