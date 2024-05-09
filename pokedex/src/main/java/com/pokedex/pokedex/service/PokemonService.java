package com.pokedex.pokedex.service;

import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.repository.EvolutionRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
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

    public PokemonService(PokeApiService pokeApiService){
        this.pokeApiService = pokeApiService;
    }

    @Transactional
    public List<EvolutionDetail> addNewPokemon(String nameOrNumber) {
        // Verificar se o Pokémon já existe na Pokédex
        PokemonResponse pokemonResponses = pokeApiService.getPokemonNameOrNumber(nameOrNumber);

        List<EvolutionDetail> evolutionDetails = PokemonMapper.toDomain(pokemonResponses);

        evolutionDetails.forEach(evolutionDetail -> {
            evolutionDetail.setSelf(pokemonRepository.save(evolutionDetail.getSelf()));
            evolutionDetail.setEvolution(pokemonRepository.save(evolutionDetail.getEvolution()));
            evolutionRepository.save(evolutionDetail);
        });

        return evolutionDetails;
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

    public void deletePokemonByNameOrNumber(String nameOrNumber) {
        Pokemon pokemon = findByNameOrNumber(nameOrNumber);
        if (pokemon!= null){
            pokemonRepository.delete(pokemon);
        } else {
            throw new PokemonNotFoundException("Pokemon not found with name or number: " + nameOrNumber);
        }
    }

    private Pokemon findByNameOrNumber(String nameOrNumber){
        try {
            long number = Long.parseLong(nameOrNumber);
            // Se conseguiu converter para número, busca pelo número do Pokémon
            return pokemonRepository.findByNumber(number);
        } catch (NumberFormatException e) {
            // Se não conseguiu converter para número, busca pelo nome do Pokémon
            return pokemonRepository.findByName(nameOrNumber);
        }
    }

    public Pokemon getPokemonByNumber(Long number) {
        Optional<Pokemon> pokemon = pokemonRepository.findById(number);
        return pokemon.orElseThrow(NoSuchElementException::new);
    }

    public List<EvolutionDetail> getEvolutionsByPokemonNumber(Long pokemonNumber){
        return evolutionRepository.findBySelf_Number(pokemonNumber);
    }
}
