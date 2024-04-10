package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

//Classe usada para interagir com a PokeAPI para recuperar as informações sobre o Pokemon
public class PokeApiService {
    private final RestTemplate restTemplate; //classe para fazer requisições HTTP
    private final ObjectMapper objectMapper;

    public PokeApiService(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build(); //constrói as instâncias
        this.objectMapper = objectMapper;
    }

    //Busca Pokemon pelo id
    public PokemonDTO getPokemonById(Long id) throws PokemonNotFoundException {
        //Chamada GET para a PokeAPI para obter os dados do pokemon através do id, o corpo da resposta é uma string
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon/" + id, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
            throw new PokemonNotFoundException("Pokemon not found with id: " + id);
        }
        try {
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody()); //converte a resposya da API para um JSON
            return objectMapper.treeToValue(jsonNode, PokemonDTO.class); //mapeia os dados do JSON para o objeto PokemonDTO
        } catch (JsonProcessingException e){
            throw new RuntimeException("Error processing Prokemon API response", e);
        }
    }

    //Busca Pokemon pelo nome ou numero
    public PokemonDTO getPokemonNameOrNumber(String nameOrNumber) throws PokemonNotFoundException {
        if (StringUtils.isNumeric(nameOrNumber)){
            Long id = Long.parseLong(nameOrNumber);
            return getPokemonById(id);
        } else {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://pokeapi.co/api/v2/pokemon/" + nameOrNumber.toLowerCase(), String.class);
            if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new PokemonNotFoundException("Pokemon not found with name: " + nameOrNumber);
            }
            try {
                JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody()); //converte a resposya da API para um JSON
                return objectMapper.treeToValue(jsonNode, PokemonDTO.class); //mapeia os dados do JSON para o objeto PokemonDTO
            } catch (JsonProcessingException e){
                throw new RuntimeException("Error processing Prokemon API response", e);
            }
        }
    }
}
