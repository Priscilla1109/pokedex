package com.pokedex.pokedex.controller;

import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonDTO;
import com.pokedex.pokedex.repository.PokemonRepository;
import com.pokedex.pokedex.service.PokeApiService;
import com.pokedex.pokedex.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/APIs/pokedex")
public class PokemonController {
    private final PokeApiService pokeApiService;

    public PokemonController(PokeApiService pokemonService) {
        this.pokeApiService = pokemonService;
    }

    //Endpoint de Consulta de Pokemons:
    @GetMapping("/pokemon/{nameOrNumber}")
    public ResponseEntity<PokemonDTO> getPokemonNameOrNumber(@PathVariable String nameOrNumber) throws PokemonNotFoundException {
        PokemonDTO pokemon = pokeApiService.getPokemonNameOrNumber(nameOrNumber);
        return ResponseEntity.ok(pokemon);
    }
}
