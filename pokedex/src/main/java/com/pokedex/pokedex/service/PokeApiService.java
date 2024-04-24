package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//Classe usada para interagir com a PokeAPI para recuperar as informações sobre o Pokemon
@Service
@RequiredArgsConstructor
public class PokeApiService {

    private final RestTemplate restTemplate; //classe para fazer requisições HTTP

    private final ObjectMapper objectMapper;


    //Busca Pokemon pelo number
    public PokemonResponse getPokemonByNumber(Long number) throws PokemonNotFoundException {
        //Chamada GET para a PokeAPI para obter os dados do pokemon através do number, o corpo da resposta é uma string

        try {
            ResponseEntity<PokemonResponse> responseEntity = restTemplate.getForEntity("http://localhost:8083/APIs/api-pokedex/pokemon/" + number, PokemonResponse.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new PokemonNotFoundException("Pokemon not found with number: " + number);
        }
    }

    //Busca Pokemon pelo nome ou numero
    public PokemonResponse getPokemonNameOrNumber(String nameOrNumber) throws PokemonNotFoundException {
        if (StringUtils.isNumeric(nameOrNumber)){
            Long id = Long.parseLong(nameOrNumber);
            return getPokemonByNumber(id);
        } else {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8083/APIs/api-pokedex/pokemon/" + nameOrNumber.toLowerCase(), String.class);
            if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new PokemonNotFoundException("Pokemon not found with name: " + nameOrNumber);
            }
            try {
                return objectMapper.readValue(responseEntity.getBody(), PokemonResponse.class);
            } catch (JsonProcessingException e){
                throw new RuntimeException("Error processing Prokemon API response", e);
            }
        }
    }

    //Lista de evoluções
    public List<EvolutionChain> getEvolutionsByPokemonName(String name) {
        //Buscar a espécie do Pokémon pelo nome
        Species specie = getSpecieByName(name);

        //Buscar a cadeia de evolução pela URL da espécie
        EvolutionChain evolutionChain = getEvolutionChainByUrl(specie.getUrl());

        //Converter a cadeia de evolução em uma lista de evoluções
        return parseEvolutionChainToList(evolutionChain.getChain());
    }

    private List<EvolutionChain> parseEvolutionChainToList(ChainLink chainLink) {
        List<EvolutionChain> evolutions = new ArrayList<>();

        EvolutionChain evolutionPokemon = new EvolutionChain();
        evolutionPokemon.getChain().getSpecies().setName(Constant.NAME_BULBASAUR);

        if (chainLink.getEvolutionDetails() != null && !chainLink.getEvolutionDetails().isEmpty()) {
            EvolutionDetail evolutionDetail = chainLink.getEvolutionDetails().get(0); //primeiro detalhe de evolução
            evolutionDetail.setTriggerName(Constant.TRIGGER_NAME_BULBASAUR);
            evolutionDetail.setItemName(Constant.ITEM_NAME_BULBASAUR);
            evolutionDetail.setMinLevel(Constant.MIN_LEVEL_BULBASAUR);
        }

        evolutions.add(evolutionPokemon);

        if (chainLink.getEvolvesTo() != null && !chainLink.getEvolvesTo().isEmpty()) {
            for (ChainLink evolvesTo : chainLink.getEvolvesTo()) {
                evolutions.addAll(parseEvolutionChainToList(evolvesTo));
            }
        }
        return evolutions;
    }

    public Species getSpecieByName(String name) {
        try {
            ResponseEntity<Species> responseEntity = restTemplate.getForEntity("http://localhost:8083/APIs/api-pokedex/species/" + name, Species.class);
            return responseEntity.getBody();
        } catch (HttpClientErrorException.NotFound e){
            throw new PokemonNotFoundException("Pokemon species is not found with name: " + name);
        }
    }

    public EvolutionChain getEvolutionChainByUrl(String url){
        ResponseEntity<EvolutionChain> responseEntity = restTemplate.getForEntity(url, EvolutionChain.class);

        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
            throw new PokemonNotFoundException("Evolution chain not found.");
        }
        try{
            JsonNode jsonNode = objectMapper.readTree(String.valueOf(responseEntity.getBody()));
            return parseEvolutionChain(jsonNode);
        } catch (JsonProcessingException e){
            throw new RuntimeException("Error processing Pokemon API response.", e);
        }
    }

    private EvolutionChain parseEvolutionChain(JsonNode jsonNode) throws JsonProcessingException {
        JsonNode chainNode = jsonNode.get("chain");

        return objectMapper.treeToValue(chainNode, EvolutionChain.class);
    }
}
