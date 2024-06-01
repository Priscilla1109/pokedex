package com.pokedex.pokedex.service;

import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.repository.EvolutionDetailRepository;
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
    private EvolutionDetailRepository evolutionDetailRepository;

    @Transactional
    public List<EvolutionDetail> addNewPokemon(String nameOrNumber) {
        PokemonResponse pokemonResponse = pokeApiService.getPokemonNameOrNumber(nameOrNumber);
        List<EvolutionDetail> evolutionDetails = PokemonMapper.toDomain(pokemonResponse);

        for (EvolutionDetail evolutionDetail : evolutionDetails){
            Pokemon saveSelf = savePokemon(evolutionDetail.getSelf());
            savePokemonTypes(saveSelf);
            evolutionDetail.setSelf(saveSelf);

            Pokemon saveEvolution = savePokemon(evolutionDetail.getEvolution());
            savePokemonTypes(saveEvolution);
            evolutionDetail.setEvolution(saveEvolution);

            evolutionDetailRepository.save(evolutionDetail);
        }
        return evolutionDetails;
    }

    private Pokemon savePokemon(Pokemon pokemon) {
        Optional<Pokemon> pokemonOpt = pokemonRepository.findByNumber(pokemon.getNumber());
        pokemonOpt.ifPresentOrElse(
            existPokemon -> {
                existPokemon.setName(pokemon.getName());
                existPokemon.setType(pokemon.getType());
                pokemonRepository.update(existPokemon);
            },
            () -> pokemonRepository.insert(pokemon)
        );
        return pokemonRepository.findByNumber(pokemon.getNumber())
            .orElseThrow(() -> new RuntimeException("Pokemon nt found after save"));
    }

    private void savePokemonTypes(Pokemon pokemon) {
        for (String typeName : pokemon.getType()) {
            Optional<TypePokemon> typePokemonOptional = typeRepository.findByName(typeName);

            TypePokemon type;
            if (typePokemonOptional.isPresent()){
                type = typePokemonOptional.get();
            } else {
                type = new TypePokemon();
                type.setName(typeName);
                Long typeId = typeRepository.save(type);
            }
            typeRepository.saveTypePokemon(pokemon.getNumber(), type.getName());
        }
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
