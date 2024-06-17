package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.Pokemon;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PokemonRepository {

    private final JdbiPokemonRepository jdbiPokemonRepository;
    private final JdbiTypeRepository jdbiTypeRepository;

    public Pokemon save(final Pokemon pokemon) throws Throwable {
        Optional<Pokemon> pokemonOpt = jdbiPokemonRepository.findByNumber(pokemon.getNumber());

        pokemonOpt.ifPresentOrElse(existPokemon -> update(pokemon), () -> insert(pokemon));

        return jdbiPokemonRepository.findByNumber(pokemon.getNumber())
            .orElseThrow(() -> new RuntimeException("Failed to save or update the Pokemon"));
    }

    public void update(final Pokemon pokemon) {
        List<String> currentTypes = jdbiTypeRepository.findByTypePokemonNumber(pokemon.getNumber());
        List<String> newTypes = pokemon.getType();

        //remove
        currentTypes.stream()
            .filter(type -> !newTypes.contains(type))
            .forEach(type -> jdbiTypeRepository.deleteTypePokemon(pokemon.getNumber(), type));

        //adiciona novos
        newTypes.stream()
            .filter(type -> !newTypes.contains(type))
            .forEach(type -> {
                if (!jdbiTypeRepository.existsTypesPokemon(pokemon.getNumber(), type)){
                    jdbiTypeRepository.saveTypePokemon(pokemon.getNumber(), type);
                }
            });

        jdbiPokemonRepository.update(pokemon);
    }

    public void insert(final Pokemon pokemon) {
        jdbiPokemonRepository.insert(pokemon);
        savePokemonTypes(pokemon);
    }

    private void savePokemonTypes(Pokemon pokemon) {
        pokemon.getType().forEach(typeName -> jdbiTypeRepository.saveTypePokemon(pokemon.getNumber(), typeName));
    }

    public void deletePokemon(Long pokemonNumber) {
        jdbiPokemonRepository.deletePokemon(pokemonNumber);
    }

    public Optional<Pokemon> getPokemonByNameOrNumber(String nameOrNumber) {
        Long pokemonNumber = null;
        try {
            pokemonNumber = Long.parseLong(nameOrNumber);
        } catch (NumberFormatException e) {
        }

        if (pokemonNumber != null) {
            return jdbiPokemonRepository.findByNumber(pokemonNumber);
        } else {
            return jdbiPokemonRepository.findByName(nameOrNumber);
        }
    }
}