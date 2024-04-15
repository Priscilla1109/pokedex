package com.pokedex.pokedex.service;

import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonResponse;
import com.pokedex.pokedex.model.PokemonResquest;
import com.pokedex.pokedex.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

//Classe que representa as regras de negócio
@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokeApiService pokeApiService;


    public PokemonResponse addNewPokemon(PokemonResquest pokemonResquest) {
        Pokemon pokemon = PokemonMapper.toDomain(pokemonResquest);
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        return new PokemonResponse(savedPokemon.getNumber(), savedPokemon.getName(), savedPokemon.getDescription(), savedPokemon.getImageUrl(), savedPokemon.getEvolutionsList());
    }

    public Page<Pokemon> listPokemons(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return pokemonRepository.findAll(pageRequest);
    }

    public boolean deletPokemon(Long number) {
        Pokemon pokemon = searchPokemonNumber(number);
        pokemonRepository.delete(pokemon);
        return true;
    }

    private Pokemon searchPokemonNumber(Long number) {
        Optional<Pokemon> pokemon = pokemonRepository.findById(number);
        return pokemon.orElseThrow(NoSuchElementException::new);
    }
}
