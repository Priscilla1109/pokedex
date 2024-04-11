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

@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokeApiService pokeApiService;


    public PokemonResponse addNewPokemon(PokemonResquest pokemonResquest) {
        Pokemon pokemon = PokemonMapper.toDomain(pokemonResquest);

        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        return new PokemonResponse(savedPokemon.getId(), savedPokemon.getName(), savedPokemon.getDescription(), savedPokemon.getImageUrl(), savedPokemon.getEvolutionsList());
    }

    public Page<Pokemon> listPokemons(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return pokemonRepository.findAll(pageRequest);
    }
}
