package com.pokedex.pokedex.service;

import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new PokemonNotFoundException("Pokemon not found with name or number: " + nameOrNumber);
        }
    }
}
