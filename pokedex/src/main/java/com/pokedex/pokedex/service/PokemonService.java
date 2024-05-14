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
import java.util.stream.Collectors;

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
        Page<EvolutionDetail> evolutionPage = evolutionRepository.findAll(pageable);

        List<PokemonResponse> pokemonResponses = new ArrayList<>();

        for (EvolutionDetail evolutionDetail : evolutionPage.getContent()) {
            Pokemon pokemon = evolutionDetail.getSelf();

            // Busca todas as evoluções associadas a este Pokémon
            List<EvolutionDetail> allEvolutions = evolutionRepository.findBySelf_Name(pokemon.getName());

            // Mapeia o Pokémon e suas evoluções para a resposta
            PokemonResponse pokemonResponse = PokemonMapper.toResponse(pokemon);
            pokemonResponse.setEvolutions(PokemonMapper.toResponseList(allEvolutions));

            // Adiciona o Pokémon mapeado à lista de respostas
            pokemonResponses.add(pokemonResponse);
        }

        return new PokemonPageResponse(pokemonResponses,
                new Meta(evolutionPage.getNumber(), evolutionPage.getSize(), (int) evolutionPage.getTotalElements(), evolutionPage.getTotalPages()));
    }

    @Transactional //garantir que ele seja executado dentro de uma transação gerenciada pelo Spring
    public void deletePokemonByNameOrNumber(String nameOrNumber) {
        PokemonResponse pokemon = getPokemonByNameOrNumber(nameOrNumber);
        if (pokemon == null) {
            throw new PokemonNotFoundException("Pokemon with name or number " + nameOrNumber + " not found.");
        }

        // Excluir os registros dependentes na tabela EVOLUTION_DETAILS
        evolutionRepository.deleteBySelfNumber(pokemon.getNumber());

        // Finalmente, excluir o próprio Pokémon da tabela POKEMONS
        Pokemon pokemonDelete = PokemonMapper.toPokemon(pokemon);
        pokemonRepository.delete(pokemonDelete);
    }

    private PokemonResponse getPokemonByNameOrNumber(String nameOrNumber){
        Pokemon pokemon;
        List<EvolutionDetail> evolutionDetails;

        if (nameOrNumber.matches("\\d+")) {
            Long number = Long.parseLong(nameOrNumber);
            pokemon = pokemonRepository.findByNumber(number);
            evolutionDetails = evolutionRepository.findBySelf_Number(number);
        } else {
            pokemon = pokemonRepository.findByName(nameOrNumber);
            evolutionDetails = evolutionRepository.findBySelf_Name(nameOrNumber);
        }

        if (pokemon != null) {
            PokemonResponse pokemonResponse = PokemonMapper.toResponse(pokemon);
            // Verifica se há detalhes de evolução e mapeia-os para a resposta do Pokémon
            if (evolutionDetails != null && !evolutionDetails.isEmpty()) {
                pokemonResponse.setEvolutions(evolutionDetails.stream()
                        .map(evolutionDetail -> PokemonMapper.toResponse(evolutionDetail.getEvolution()))
                        .collect(Collectors.toList()));
            }
            return pokemonResponse;
        } else {
            // Retorna null se o Pokémon não for encontrado
            return null;
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
