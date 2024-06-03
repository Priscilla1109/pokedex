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
        savePokemonTypes(pokemon);
            
        return jdbiPokemonRepository.findByNumber(pokemon.getNumber())
            .orElseThrow(() -> new RuntimeException("Failed to save or update the Pokemon"));
    }

    public void update(final Pokemon pokemon) {
            jdbiPokemonRepository.update(pokemon);
        }
        
    public void insert(final Pokemon pokemon) {
            jdbiPokemonRepository.insert(pokemon);
        }

    private void savePokemonTypes(Pokemon pokemon) {
        List<String> types = pokemon.getType();
        if (types == null || types.isEmpty()) {
            throw new IllegalArgumentException("Pokemon type cannot be null or empty");
        }

        for (String typeName : types) {
            System.out.println("Searching for type: " + typeName);
            Optional<TypePokemon> typePokemonOptional = typeRepository.findByType(typeName);

            TypePokemon type;
            if (typePokemonOptional.isPresent()) {
                type = typePokemonOptional.get();
            } else {
                type = new TypePokemon();
                type.setType(typeName);
                Long typeId = typeRepository.save(type);
                type.setPokemonNumber(typeId); // Corrigido para usar o ID correto
            }
            typeRepository.saveTypePokemon(pokemon.getNumber(), type.getType());
        }
    }
}