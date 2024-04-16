package com.pokedex.pokedex.controller;

import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.service.PokeApiService;
import com.pokedex.pokedex.service.PokemonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/APIs/pokedex")
public class PokemonController {
    private PokeApiService pokeApiService;
    private PokemonService pokemonService;



    //Endpoint de Consulta de Pokemons:
    @GetMapping("/pokemon/{nameOrNumber}")
    public ResponseEntity<PokemonResquest> getPokemonNameOrNumber(@PathVariable String nameOrNumber) throws PokemonNotFoundException {
        PokemonResquest pokemon = pokeApiService.getPokemonNameOrNumber(nameOrNumber);
        return ResponseEntity.ok(pokemon);
    }

    //Endpoint de Adição de Pokemons:
    @PostMapping("/add")
    public ResponseEntity<String> addNewPokemon(@Valid @RequestBody PokemonResquest pokemonResquest){
        Pokemon pokemon = PokemonMapper.toDomain(pokemonResquest);
        pokemonService.addNewPokemon(pokemonResquest);

        return ResponseEntity.ok("Pokemon adicionado com sucesso!");
    }

    //Endpoint de Listagem de Pokemons:
    @GetMapping("/pokemons")
    public ResponseEntity<PokemonPageResponse> listPokemons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        Page<Pokemon> pokemonPage = pokemonService.listPokemons(page, pageSize);

        PokemonPageResponse response = new PokemonPageResponse();
        response.setPokemons(pokemonPage.getContent());
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
    public ResponseEntity<String> deletarPokemon(@PathVariable Long number){
        pokemonService.deletPokemon(number);
        return new ResponseEntity<>("Pokemons deletado com sucesso!", HttpStatus.OK);
    }

    @GetMapping("/species/{name}")
    public ResponseEntity<PokemonSpecie> getSpeciesByName(@PathVariable String name){
        PokemonSpecie species = pokeApiService.getSpecieByName(name);
        return ResponseEntity.ok(species);
    }

    @GetMapping("/evolution-chain/{number}")
    public ResponseEntity<EvolutionChain> getEvolutionChainByUrl(@PathVariable String url){
        EvolutionChain chain = pokeApiService.getChain(url);
        return ResponseEntity.ok(chain);
    }
}
