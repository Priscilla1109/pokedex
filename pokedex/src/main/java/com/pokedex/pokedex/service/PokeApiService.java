package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.exception.InternalServerErrorException;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.model.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;

//Classe usada para interagir com a api-pokedex para recuperar as informações sobre o Pokemon
@Service
public class PokeApiService {

    private final RestTemplate restTemplate; //classe para fazer requisições HTTP

    @Autowired
    public PokeApiService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    //Busca Pokemon pelo nome ou numero
    public PokemonResponse getPokemonNameOrNumber(String nameOrNumber) throws PokemonNotFoundException {
        try {
            ResponseEntity<PokemonResponse> responseEntity = restTemplate.getForEntity(
                    "http://localhost:8083/api-pokedex/v2/pokemon/" + nameOrNumber,
                    PokemonResponse.class
            );

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity.getBody();
            } else if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PokemonNotFoundException("Pokemon not found with name or number: " + nameOrNumber);
            } else {
                // Lançar uma exceção genérica para outros códigos de erro HTTP
                throw new InternalServerErrorException("Erro ao acessar a API PokeAPI. Código de erro HTTP: " + responseEntity.getStatusCodeValue());
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PokemonNotFoundException("Pokemon not found with name or number: " + nameOrNumber);
            } else {
                // Lançar uma exceção genérica para outros códigos de erro HTTP
                throw new InternalServerErrorException("Erro ao acessar a API PokeAPI. Código de erro HTTP: " + e.getStatusCode().value());
            }
        }
    }

}
