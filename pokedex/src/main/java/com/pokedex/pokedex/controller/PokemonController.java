package com.pokedex.pokedex.controller;

import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.service.PokeApiService;
import com.pokedex.pokedex.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/APIs/pokedex")
@RequiredArgsConstructor
public class PokemonController {

    private final PokeApiService pokeApiService;

    private final PokemonService pokemonService;

    //Endpoint de Consulta de Pokemons:
    @GetMapping("/pokemon/{nameOrNumber}")
    public ResponseEntity<List<PokemonResponse>> getPokemonNameOrNumber(@PathVariable String nameOrNumber) throws PokemonNotFoundException {
        List<PokemonResponse> pokemonList = pokeApiService.getPokemonNameOrNumber(nameOrNumber);
        return ResponseEntity.ok(pokemonList);
    }

    //Endpoint de Adição de Pokemons:
    @PostMapping("/add/{nameOrNumber}")
    public ResponseEntity<List<EvolutionDetail>> addNewPokemon(@PathVariable String nameOrNumber) {
        List<EvolutionDetail> evolutionDetails = pokemonService.addNewPokemon(nameOrNumber);
        return ResponseEntity.ok(evolutionDetails);
    }

    //Endpoint de Listagem de Pokemons:
    @GetMapping("/pokemons")
    public ResponseEntity<PokemonPageResponse> listPokemons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize){
        // Recupera a lista de PokémonPageResponse
        PokemonPageResponse pokemonPage = pokemonService.listPokemons(page, pageSize);

        return ResponseEntity.ok(pokemonPage);
    }

    //Endpoint de Deleção de Pokemons:
    @DeleteMapping("{number}")
    public ResponseEntity<String> deletePokemon(@PathVariable Long number){
        pokemonService.deletePokemon(number);
        return new ResponseEntity<>("Pokemons deletado com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/evolutions/{pokemonNumber}")
    public ResponseEntity<List<EvolutionDetail>> getEvolutionPokemonByNumber(@PathVariable Long pokemonNumber){
        List<EvolutionDetail> evolutionDetails = pokemonService.getEvolutionsByPokemonNumber(pokemonNumber);
        return ResponseEntity.ok(evolutionDetails);
    }
}
