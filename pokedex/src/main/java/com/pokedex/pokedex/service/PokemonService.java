package com.pokedex.pokedex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.repository.EvolutionRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

//Classe que representa as regras de negócio
@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokeApiService pokeApiService;

    @Autowired
    private EvolutionRepository evolutionRepository;

    @Autowired
    private RestTemplate restTemplate;


    public EvolutionDetail addNewPokemon(String nameOrNumber) {
        // Verificar se o Pokémon já existe na Pokédex
        PokemonResponse pokemonResponses = pokeApiService.getPokemonNameOrNumber(nameOrNumber);

        EvolutionDetail evolutionDetail = PokemonMapper.toDomain(pokemonResponses);
        //TODO: antes de inserir na base deve ser feito o seguinte: para cada pokemon do evolution detail devo salvar no pokemonRepository para depois referenciar
        return evolutionRepository.save(evolutionDetail);
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
