package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.repository.EvolutionRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//Classe que representa as regras de negócio
@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private EvolutionRepository evolutionRepository;

    @Autowired
    private PokemonMapper pokemonMapper;


    public void addNewPokemon(PokemonResquest pokemonRequest) {
        String pokemonName = pokemonRequest.getName();
        // Verificar se o Pokémon já existe na Pokédex
        ResponseEntity<String> response = restTemplate.getForEntity("https://api-pokedex.com/pokemon/" + pokemonName, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            // Parsear o JSON da resposta da API Pokédex
            ObjectMapper mapper = new ObjectMapper();
            try {
                JsonNode root = mapper.readTree(response.getBody());
                Long pokemonId = root.get("id").asLong();
                ArrayList<Pokemon> pokemonType = root.get("type").isArray();
                // Outras informações do Pokémon...

                // Criar um objeto Pokémon com as informações fornecidas pela API Pokédex
                Pokemon pokemon = new Pokemon();
                pokemon.setNumber(pokemonId);
                pokemon.setName(pokemonName);
                pokemon.setType(pokemonType);
                // Outras informações do Pokémon...

                // Adicionar o Pokémon à Pokédex
                pokemonRepository.save(pokemon);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            // Pokémon não encontrado na Pokédex
            System.out.println("Pokemon not found in the Pokédex.");
        }
    }

    public PokemonPageResponse listPokemons(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Pokemon> pokemonPage = pokemonRepository.findAll(pageable);

        List<PokemonResponse> pokemonsWithEvolutions = new ArrayList<>();
        for (Pokemon pokemon : pokemonPage.getContent()) {
            PokemonResponse pokemonResponse = PokemonMapper.toResponse(pokemon); // Converter Pokemon para PokemonResponse
            pokemonsWithEvolutions.add(pokemonResponse);
        }

        return new PokemonPageResponse(pokemonsWithEvolutions,
                new Meta(pokemonPage.getNumber(),
                        pokemonPage.getSize(), (int) pokemonPage.getTotalElements(), pokemonPage.getTotalPages())); // Ajuste conforme necessário para criar a resposta correta
    }

    public static Pokemon mapPokemonWithEvolutions(PokemonResponse pokemonResponse) {
        Pokemon pokemon = new Pokemon();
        pokemon.setNumber(pokemonResponse.getNumber());
        pokemon.setName(pokemonResponse.getName());
        pokemon.setType(pokemonResponse.getType());
        pokemon.setImageUrl(pokemonResponse.getImageUrl());

        List<EvolutionPokemon> evolutions = new ArrayList<>();
        if (pokemonResponse.getEvolutions() != null) {
            for (EvolutionPokemon evolutionPokemon : pokemonResponse.getEvolutions()) {
                EvolutionPokemon evolution = new EvolutionPokemon();
                evolution.setSelf(pokemon);
                evolution.setNumber(evolutionPokemon.getNumber());
                evolutions.add(evolution);
            }
        }
        pokemon.setEvolutions(evolutions);

        return pokemon;
    }

    public boolean deletePokemon(Long number) {
        Pokemon pokemon = getPokemonByNumber(number);
        pokemonRepository.delete(pokemon);
        return true;
    }

    public Pokemon getPokemonByNumber(Long number) {
        Optional<Pokemon> pokemon = pokemonRepository.findById(number);
        return pokemon.orElseThrow(NoSuchElementException::new);
    }

    public List<EvolutionDetail> getEvolutionsByPokemonNumber(Long pokemonNumber){
        return evolutionRepository.findBySelf_Number(pokemonNumber);
    }
}
