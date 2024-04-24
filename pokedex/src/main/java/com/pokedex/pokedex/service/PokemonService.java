package com.pokedex.pokedex.service;

import com.pokedex.pokedex.model.EvolutionDetail;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.repository.EvolutionRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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


    public Pokemon addNewPokemon(Pokemon pokemon) {
        pokemonRepository.save(pokemon);
        return pokemon;
    }

    public Page<Pokemon> listPokemons(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return pokemonRepository.findAll(pageRequest);
    }

    public boolean deletePokemon(Long number) {
        Pokemon pokemon = searchPokemonNumber(number);
        pokemonRepository.delete(pokemon);
        return true;
    }

    private Pokemon searchPokemonNumber(Long number) {
        Optional<Pokemon> pokemon = pokemonRepository.findById(number);
        return pokemon.orElseThrow(NoSuchElementException::new);
    }

    public List<EvolutionDetail> getEvolutionsByPokemonNumber(Long pokemonNumber){
        return evolutionRepository.findByPokemonNumber(pokemonNumber);
    }
}
