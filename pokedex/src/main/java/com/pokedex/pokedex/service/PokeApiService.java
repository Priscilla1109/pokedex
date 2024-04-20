package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.model.*;
import org.apache.commons.lang3.StringUtils;
import com.google.gson.Gson;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//Classe usada para interagir com a PokeAPI para recuperar as informações sobre o Pokemon
@Service
public class PokeApiService {
    private final RestTemplate restTemplate; //classe para fazer requisições HTTP
    private final ObjectMapper objectMapper;
    private final Gson gson;

    public PokeApiService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build(); //constrói as instâncias
        this.objectMapper = objectMapper;
        this.gson = new Gson();
    }

    //Busca Pokemon pelo id
    public PokemonResponse getPokemonByNumber(Long number) throws PokemonNotFoundException {
        //Chamada GET para a PokeAPI para obter os dados do pokemon através do id, o corpo da resposta é uma string
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon/ditto" + number, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
            throw new PokemonNotFoundException("Pokemon not found with id: " + number);
        }
        try {
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody()); //converte a resposya da API para um JSON
            return objectMapper.treeToValue(jsonNode, PokemonResponse.class); //mapeia os dados do JSON para o objeto PokemonDTO
        } catch (JsonProcessingException e){
            throw new RuntimeException("Error processing Prokemon API response", e);
        }
    }

    //Busca Pokemon pelo nome ou numero
    public PokemonResponse getPokemonNameOrNumber(String nameOrNumber) throws PokemonNotFoundException {
        if (StringUtils.isNumeric(nameOrNumber)){
            Long id = Long.parseLong(nameOrNumber);
            return getPokemonByNumber(id);
        } else {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon/ditto" + nameOrNumber.toLowerCase(), String.class);
            if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new PokemonNotFoundException("Pokemon not found with name: " + nameOrNumber);
            }
            try {
                JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody()); //converte a resposya da API para um JSON
                return objectMapper.treeToValue(jsonNode, PokemonResponse.class); //mapeia os dados do JSON para o objeto PokemonDTO
            } catch (JsonProcessingException e){
                throw new RuntimeException("Error processing Prokemon API response", e);
            }
        }
    }

    //Lista de evoluções
    public List<EvolutionPokemon> getEvolutionByName(String name){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon-species/" + name.toLowerCase(), String.class);
        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
            throw new PokemonNotFoundException("Pokemon species is not found with name: " + name);
        }
        try {
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            JsonNode evolutionChainUrlNode = jsonNode.get("evolution_chain").get("url");
            String evolutionChainUrl = evolutionChainUrlNode.asText();

            ResponseEntity<String> evolutionResponseEntity = restTemplate.getForEntity(evolutionChainUrl, String.class);
            JsonNode evolutionJsonNode = objectMapper.readTree(evolutionResponseEntity.getBody());

            return parseEvolutionPokemon(evolutionJsonNode);
        } catch (Exception e){
            throw new RuntimeException("Error processing Pokemon API response: ", e);
        }
    }


    //Método que converte o JSON da evolução do Pokemon em uma lista de Objetos EvolutionPokemon
    public List<EvolutionPokemon> parseEvolutionPokemon(JsonNode evolutionJsonNode) {
        List<EvolutionPokemon> evolutions = new ArrayList<>();
        for (JsonNode chainNode : evolutionJsonNode.get("chain")){
            EvolutionPokemon evolutionPokemon = new EvolutionPokemon();

            evolutionPokemon.setSpeciesName(chainNode.get("species").get("name").asText());
            JsonNode evolutionDetails = chainNode.get("evolution_details").get(0);

            evolutionPokemon.setEvolutionMethod(evolutionDetails.get("trigger").get("name").asText());
            evolutionPokemon.setTrigger(evolutionDetails.get("trigger").get("name").asText());

            if (evolutionDetails.has("item")){
                evolutionPokemon.setItem(evolutionDetails.get("item").get("name").asText());
            }

            if (evolutionDetails.has("min_level")){
                evolutionPokemon.setLevel(evolutionDetails.get("min_level").asInt());
            }

            evolutions.add(evolutionPokemon);
        }
        return evolutions;
    }

    public PokemonSpecie getSpecieByName(String name) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon-species/" + name.toLowerCase(), String.class);
        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
            throw new PokemonNotFoundException("Pokemon species is not found with name: " + name);
        }
        try {
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            return objectMapper.treeToValue(jsonNode.get("name"), PokemonSpecie.class);
        } catch (JsonProcessingException e){
            throw new RuntimeException("Error processing Pokemon API response.", e);
        }
    }

    public EvolutionChain getChain(String url){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
            throw new PokemonNotFoundException("Evolution chain not found.");
        }
        try{
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
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
