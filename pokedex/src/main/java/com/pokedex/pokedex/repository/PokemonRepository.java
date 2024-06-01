package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.Pokemon;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
    @RequiredArgsConstructor
    public class PokemonRepository {
        
    private final JdbiPokemonRepository repository;
        
    public Pokemon save(final Pokemon pokemon) throws Throwable {
        Optional<Pokemon> pokemonOpt = repository.findByNumber(pokemon.getNumber());

        pokemonOpt.ifPresentOrElse(existPokemon -> update(pokemon), () -> insert(pokemon));
            
        return repository.findByNumber(pokemon.getNumber())
            .orElseThrow(() -> new RuntimeException("Failed to save or update the Pokemon"));
    }

    public void update(final Pokemon pokemon) {
            repository.update(pokemon);
        }
        
    public void insert(final Pokemon pokemon) {
            repository.insert(pokemon);
        }

}