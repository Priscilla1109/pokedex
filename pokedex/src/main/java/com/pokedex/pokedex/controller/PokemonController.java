package com.pokedex.pokedex.controller;

import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.service.PokeApiService;
import com.pokedex.pokedex.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/APIs/pokedex")
@RequiredArgsConstructor
public class PokemonController {

    private final PokeApiService pokeApiService;

    private final PokemonService pokemonService;

    //Endpoint de Consulta de Pokemons:
    @GetMapping("/pokemon/{nameOrNumber}")
    public ResponseEntity<PokemonResponse> getPokemonNameOrNumber(@PathVariable String nameOrNumber) throws PokemonNotFoundException {
        PokemonResponse pokemonResponse = pokeApiService.getPokemonNameOrNumber(nameOrNumber);
        return ResponseEntity.ok(pokemonResponse);
    }

    //Endpoint de Adição de Pokemons:
    @PostMapping("/add/{nameOrNumber}")
    public ResponseEntity<PokemonResponse> addNewPokemon(@PathVariable String nameOrNumber) throws Throwable {
        List<EvolutionDetail> evolutionDetails = pokemonService.addNewPokemon(nameOrNumber);
        PokemonResponse pokemonResponse = pokeApiService.getPokemonNameOrNumber(nameOrNumber);

        return ResponseEntity.ok(pokemonResponse);
    }

    //Endpoint de Listagem de Pokemons:
    @GetMapping("/pokemons")
    public ResponseEntity<PokemonPageResponse> listPokemons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize){
        PokemonPageResponse pokemonPage = pokemonService.listPokemons(page, pageSize);

        return ResponseEntity.ok(pokemonPage);
    }

    //Endpoint de Deleção de Pokemons:
    @DeleteMapping("/pokemon/{pokemonNameOrNumber}")
    public ResponseEntity<String> deletePokemon(@PathVariable String pokemonNameOrNumber) {
        pokemonService.deletePokemonByNameOrNumber(pokemonNameOrNumber);
        return ResponseEntity.ok("Pokemon deleted successfully");
    }


    //Endpoint de Busca de Evoluções dos Pokemons:
    @GetMapping("/evolutions/{pokemonNumber}")
    public ResponseEntity<PokemonResponse> getEvolutionPokemonByNumber(@PathVariable Long pokemonNumber){
        PokemonResponse pokemonResponse = pokemonService.getEvolutionsByPokemonNumber(pokemonNumber);
        return ResponseEntity.ok(pokemonResponse);
    }
}
