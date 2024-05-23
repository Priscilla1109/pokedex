package com.pokedex.pokedex.service;

import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.repository.EvolutionRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import com.pokedex.pokedex.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TypeRepository typeRepository;

    @Autowired
    private EvolutionRepository evolutionRepository;

    @Transactional
    public List<EvolutionDetail> addNewPokemon(String nameOrNumber) {
        // Verificar se o Pokémon já existe na Pokédex
        PokemonResponse pokemonResponses = pokeApiService.getPokemonNameOrNumber(nameOrNumber);

        List<EvolutionDetail> evolutionDetails = PokemonMapper.toDomain(pokemonResponses);

        evolutionDetails.forEach(evolutionDetail -> {
            Pokemon self = pokemonRepository.save(evolutionDetail.getSelf());
            evolutionDetail.setEvolution(self);
            Pokemon evolution = pokemonRepository.save(evolutionDetail.getEvolution());
            evolutionDetail.setEvolution(evolution);
            evolutionRepository.save(evolutionDetail);

            self.getType().forEach(typeName ->{
                TypePokemon type = typeRepository.findByName(typeName).orElseGet(() -> {
                    TypePokemon newType = new TypePokemon();
                    newType.setName(typeName);
                    return typeRepository.save(newType);
                });
                typeRepository.saveTypePokemon(self.getNumber(), type.getName());
            });
        });

        return evolutionDetails;
    }

//    public PokemonPageResponse listPokemons(int page, int pageSize) {
//        Pageable pageable = PageRequest.of(page, pageSize);
//        Page<EvolutionDetail> evolutionPage = evolutionRepository.findAll(pageable);
//
//        Set<PokemonResponse> pokemonResponses = new HashSet<>();
//
//        for (EvolutionDetail evolutionDetail : evolutionPage.getContent()){
//            Pokemon pokemon = evolutionDetail.getSelf();
//            PokemonResponse pokemonResponse = PokemonMapper.toResponse(pokemon);
//
//            List<EvolutionDetail> allEvolutions = evolutionRepository.findBySelf_Number(pokemon.getNumber());
//            pokemonResponse.setEvolutions(PokemonMapper.toResponseList(allEvolutions));
//            pokemonResponses.add(pokemonResponse);
//        }
//
//        return new PokemonPageResponse(new ArrayList<>(pokemonResponses),
//                new Meta(evolutionPage.getNumber(), evolutionPage.getSize(), (int) evolutionPage.getTotalElements(), evolutionPage.getTotalPages()));
//    }
//
//    @Transactional //garantir que ele seja executado dentro de uma transação gerenciada pelo Spring
//    public void deletePokemonByNameOrNumber(String nameOrNumber) {
//        Pokemon pokemon = getPokemonByNameOrNumber(nameOrNumber);
//        if (pokemon == null) {
//            throw new PokemonNotFoundException("Pokemon with name or number " + nameOrNumber + " not found.");
//        }
//
//        evolutionRepository.deleteBySelfNumber(pokemon.getNumber());
//
//        pokemonRepository.delete(pokemon);
//    }
//
//    private Pokemon getPokemonByNameOrNumber(String nameOrNumber){
//        try {
//            long number = Long.parseLong(nameOrNumber);
//            return pokemonRepository.findByNumber(number);
//        } catch (NumberFormatException e) {
//            return pokemonRepository.findByName(nameOrNumber);
//        }
//    }
//
//    public Pokemon getPokemonByNumber(Long number) {
//        Optional<Pokemon> pokemon = pokemonRepository.findById(number);
//        return pokemon.orElseThrow(NoSuchElementException::new);
//    }
//
//    public PokemonResponse getEvolutionsByPokemonNumber(Long pokemonNumber){
//        // Verifica se há evoluções para o Pokémon com o número fornecido
//        List<EvolutionDetail> evolutionDetail = evolutionRepository.findBySelf_Number(pokemonNumber);
//        if (evolutionDetail != null | !evolutionDetail.isEmpty()) {
//            return null;
//        }
//
//        EvolutionDetail mainPokemonDetail = evolutionDetail.get(0);
//        Pokemon mainPokemon =mainPokemonDetail.getSelf();
//
//        PokemonResponse pokemonResponse = PokemonMapper.toResponse(mainPokemon);
//        List<PokemonResponse> evolutionresponse = getEvolutionsResponse(evolutionDetail);
//        pokemonResponse.setEvolutions(evolutionresponse);
//
//        return pokemonResponse;
//    }
//
//    private List<PokemonResponse> getEvolutionsResponse(List<EvolutionDetail> evolutionDetails){
//        return evolutionDetails.stream()
//            .map(detail -> PokemonMapper.toResponse(detail.getEvolution()))
//            .collect(Collectors.toList());
//    }
}
