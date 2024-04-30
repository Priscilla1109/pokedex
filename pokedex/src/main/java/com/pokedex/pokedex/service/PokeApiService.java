package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
            ResponseEntity<PokemonResponse> responseEntity = restTemplate.getForEntity("http://localhost:8083/api-pokedex/v2/pokemon/" + number, PokemonResponse.class);
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
}
