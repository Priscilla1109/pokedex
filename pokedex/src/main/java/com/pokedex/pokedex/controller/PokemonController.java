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
    public ResponseEntity<PokemonResponse> getPokemonNameOrNumber(@PathVariable String nameOrNumber) throws PokemonNotFoundException {
        PokemonResponse pokemon = pokeApiService.getPokemonNameOrNumber(nameOrNumber);
        return ResponseEntity.ok(pokemon);
    }

    //Endpoint de Adição de Pokemons:
    @PostMapping("/add")
    public ResponseEntity<String> addNewPokemon(@RequestBody PokemonResquest pokemonRequest) {
        String pokemonName = pokemonRequest.getName();

        // Verificar se o Pokémon já existe na API Pokedex
        PokemonResponse existingPokemon = pokeApiService.getPokemonNameOrNumber(pokemonName);

        if (existingPokemon != null) {
            // Se o Pokémon já existe, use os dados do Pokémon existente para adicionar um novo Pokémon
            Pokemon newPokemon = new Pokemon();
            newPokemon.setName(existingPokemon.getName());
            newPokemon.setNumber(existingPokemon.getNumber());
            newPokemon.setType(existingPokemon.getType());
            newPokemon.setImageUrl(existingPokemon.getImageUrl());

            // Mapear o Pokémon com as informações de evolução
            newPokemon = pokemonService.mapPokemonWithEvolutions(newPokemon);

            pokemonService.addNewPokemon(newPokemon);
            return ResponseEntity.ok("Pokemon adicionado com sucesso!" + newPokemon);
        } else {
            return ResponseEntity.badRequest().body("O Pokémon não foi encontrado na API Pokedex.");
        }
    }

    //Endpoint de Listagem de Pokemons:
    @GetMapping("/pokemons")
    public ResponseEntity<PokemonPageResponse> listPokemons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize){
        // Recupera a lista de Pokémon usando o PokemonService
        List<Pokemon> pokemons = (List<Pokemon>) pokemonService.listPokemons(page, pageSize);

        // Mapear cada Pokémon com as informações de evolução
        for (Pokemon pokemon : pokemons) {
            pokemonService.mapPokemonWithEvolutions(pokemon);
        }
        Page<Pokemon> pokemonPage = pokemonService.listPokemons(page, pageSize);

        PokemonPageResponse response = new PokemonPageResponse();
        response.setPokemons(pokemons);
        response.setMeta(new Meta(
                pokemonPage.getNumber(),
                pokemonPage.getSize(),
                pokemonPage.getTotalPages(),
                pokemonPage.getTotalElements()
        ));

        return ResponseEntity.ok(response);
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
