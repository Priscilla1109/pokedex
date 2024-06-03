package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.Pokemon;

import java.util.List;
import java.util.Optional;

import com.pokedex.pokedex.model.TypePokemon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PokemonRepository {

    private final JdbiPokemonRepository jdbiPokemonRepository;
    private final TypeRepository typeRepository;

    public Pokemon save(final Pokemon pokemon) throws Throwable {
        Optional<Pokemon> pokemonOpt = jdbiPokemonRepository.findByNumber(pokemon.getNumber());

        pokemonOpt.ifPresentOrElse(existPokemon -> update(pokemon), () -> insert(pokemon));

        // Salvando os tipos antes de buscar novamente

        return jdbiPokemonRepository.findByNumber(pokemon.getNumber())
            .orElseThrow(() -> new RuntimeException("Failed to save or update the Pokemon"));
    }

    public void update(final Pokemon pokemon) {
        //TODO: remover os tipos que nao tiverem no pokemon e inserir os que tem no pokemon e na base não
        jdbiPokemonRepository.update(pokemon);
    }

    public void insert(final Pokemon pokemon) {
        jdbiPokemonRepository.insert(pokemon);
        savePokemonTypes(pokemon);
    }

    private void savePokemonTypes(Pokemon pokemon) {
        List<String> types = pokemon.getType();

        for (String typeName : types) {
            typeRepository.saveTypePokemon(pokemon.getNumber(), typeName);
        }
    }
}