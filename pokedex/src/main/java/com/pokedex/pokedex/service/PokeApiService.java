package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.model.EvoluchionChain;
import com.pokedex.pokedex.model.PokemonResquest;
import com.pokedex.pokedex.model.PokemonSpecie;
import org.apache.commons.lang3.StringUtils;
import com.google.gson.Gson;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//Classe usada para interagir com a PokeAPI para recuperar as informações sobre o Pokemon
@Service
public class PokeApiService {
    private final RestTemplate restTemplate; //classe para fazer requisições HTTP
    private final ObjectMapper objectMapper;
    private final Gson gson;

    public PokeApiService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper, Gson gson) {
        this.restTemplate = restTemplateBuilder.build(); //constrói as instâncias
        this.objectMapper = objectMapper;
        this.gson = gson;
    }

    //Busca Pokemon pelo id
    public PokemonResquest getPokemonById(Long id) throws PokemonNotFoundException {
        //Chamada GET para a PokeAPI para obter os dados do pokemon através do id, o corpo da resposta é uma string
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon/ditto" + id, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
            throw new PokemonNotFoundException("Pokemon not found with id: " + id);
        }
        try {
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody()); //converte a resposya da API para um JSON
            return objectMapper.treeToValue(jsonNode, PokemonResquest.class); //mapeia os dados do JSON para o objeto PokemonDTO
        } catch (JsonProcessingException e){
            throw new RuntimeException("Error processing Prokemon API response", e);
        }
    }

    //Busca Pokemon pelo nome ou numero
    public PokemonResquest getPokemonNameOrNumber(String nameOrNumber) throws PokemonNotFoundException {
        if (StringUtils.isNumeric(nameOrNumber)){
            Long id = Long.parseLong(nameOrNumber);
            return getPokemonById(id);
        } else {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon/ditto" + nameOrNumber.toLowerCase(), String.class);
            if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new PokemonNotFoundException("Pokemon not found with name: " + nameOrNumber);
            }
            try {
                JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody()); //converte a resposya da API para um JSON
                return objectMapper.treeToValue(jsonNode, PokemonResquest.class); //mapeia os dados do JSON para o objeto PokemonDTO
            } catch (JsonProcessingException e){
                throw new RuntimeException("Error processing Prokemon API response", e);
            }
        }
    }

    //Lista de evoluções
    public PokemonSpecie getSpecieByName(String name){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon-species/" + name.toLowerCase(), String.class);
        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
            throw new PokemonNotFoundException("Pokemon species is not found with name: " + name);
        }
        try {
            EvoluchionChain evoluchionChain = gson.fromJson(responseEntity.getBody(), EvoluchionChain.class);
        } catch (Exception e){
            throw new RuntimeException("Error processing Pokemon API response", e);
        }
    }
}
