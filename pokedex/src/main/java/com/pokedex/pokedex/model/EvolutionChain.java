package com.pokedex.pokedex.model;

import lombok.Data;

import java.util.List;

@Data
//Classe que contém o atributos da cadeia de evolução do Pokemon
public class EvolutionChain {
    private int id;
    private ChainLink chain;

    @Data
    public static class ChainLink { //representa um elo da cadeia de evolução
        private Species species;
        private List<EvolutionDetail> evolutionDetails;
        private List<ChainLink> evolvesTo;

        @Data
        public static class Species { //informações sobre a espécie do pokemon
            private String name;
            private String url;
        }

        @Data
        public static class EvolutionDetail { //detalhes sobre a evolução
            private int minLevel;
            private String triggerName;
            private String itemName;
        }
    }
}
