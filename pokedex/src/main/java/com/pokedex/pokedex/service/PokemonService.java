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

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

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
        //TODO: refatorar o codigo para usar o evolution repository
        //TODO: criar uma forma de mapear para o response agrupando os evolutionDetail pelo self

        List<PokemonResponse> pokemonsWithEvolutions = new ArrayList<>();
        for (Pokemon pokemon : pokemonPage.getContent()) {
            PokemonResponse pokemonResponse = PokemonMapper.toResponse(pokemon);
            pokemonsWithEvolutions.add(pokemonResponse);
        }

        return new PokemonPageResponse(pokemonsWithEvolutions,
                new Meta(pokemonPage.getNumber(),
                        pokemonPage.getSize(), (int) pokemonPage.getTotalElements(), pokemonPage.getTotalPages()));
    }

    @Transactional //garantir que ele seja executado dentro de uma transação gerenciada pelo Spring
    public void deletePokemonByNameOrNumber(String nameOrNumber) {
        Pokemon pokemon = findByNameOrNumber(nameOrNumber);
        if (pokemon == null) {
            throw new PokemonNotFoundException("Pokemon with name or number " + nameOrNumber + " not found.");
        }

        // Excluir os registros dependentes na tabela EVOLUTION_DETAILS
        evolutionRepository.deleteBySelfNumber(pokemon.getNumber());

        // Finalmente, excluir o próprio Pokémon da tabela POKEMONS
        pokemonRepository.delete(pokemon);
    }

    private Pokemon findByNameOrNumber(String nameOrNumber){
        try {
            long number = Long.parseLong(nameOrNumber);
            return pokemonRepository.findByNumber(number);
        } catch (NumberFormatException e) {
            return pokemonRepository.findByName(nameOrNumber);
        }
    }

    public Pokemon getPokemonByNumber(Long number) {
        Optional<Pokemon> pokemon = pokemonRepository.findById(number);
        return pokemon.orElseThrow(NoSuchElementException::new);
    }

    public List<EvolutionDetail> getEvolutionsByPokemonNumber(Long pokemonNumber){
        // Verifica se há evoluções para o Pokémon com o número fornecido
        List<EvolutionDetail> evolutionDetail = evolutionRepository.findBySelf_Number(pokemonNumber);
        if (evolutionDetail != null) {
            return evolutionDetail;
        }
        return Collections.emptyList();
    }
}
