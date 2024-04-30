package com.pokedex.pokedex.service;

import com.pokedex.pokedex.model.EvolutionDetail;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.repository.EvolutionRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

//Classe que representa as regras de negócio
@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private EvolutionRepository evolutionRepository;


    public Pokemon addNewPokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    public Page<Pokemon> listPokemons(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Pokemon> pokemonPage = pokemonRepository.findAll(pageRequest);

        List<Pokemon> pokemonWithEvolutionsList = pokemonPage.getContent().stream()
                .map(this::mapPokemonWithEvolutions)
                .collect(Collectors.toList());

        return new PageImpl<>(pokemonWithEvolutionsList, pageRequest, pokemonPage.getTotalElements());
    }

    private Pokemon mapPokemonWithEvolutions(Pokemon pokemon) {
        List<EvolutionDetail> evolutions = evolutionRepository.findByPokemonNumber(pokemon.getNumber());
        List<EvolutionDetail> evolutionsWithDetails = evolutions.stream()
                .map(this::mapEvolutionWithDetails)
                .collect(Collectors.toList());

        pokemon.setEvolutionDetails(evolutionsWithDetails);

        return pokemon;
    }

    private EvolutionDetail mapEvolutionWithDetails(EvolutionDetail evolution) {
        EvolutionDetail evolutionDetail = new EvolutionDetail();
        evolutionDetail.setTriggerName(evolution.getTriggerName());
        evolutionDetail.setMinLevel(evolution.getMinLevel());

        return evolutionDetail;
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
        return evolutionRepository.findByPokemonNumber(pokemonNumber);
    }
}
