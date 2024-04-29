package com.pokedex.pokedex.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.model.EvolutionDetail;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonPageResponse;
import com.pokedex.pokedex.model.PokemonResponse;
import com.pokedex.pokedex.repository.EvolutionRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import org.h2.mvstore.Page;
import org.hibernate.validator.constraints.br.CPF;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {
    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private EvolutionRepository evolutionRepository;

    @InjectMocks
    private PokemonService pokemonService;

    @Test
    public void testAddNewPokemon() {
        Pokemon mockPokemon = new Pokemon();
        when(pokemonRepository.save(mockPokemon)).thenReturn(mockPokemon);

        Pokemon addedPokemon = pokemonService.addNewPokemon(mockPokemon);

        assertEquals(mockPokemon, addedPokemon);
        verify(pokemonRepository, times(1)).save(mockPokemon);
    }

   /* @Test
    public void testListPokemons() {
        List<Pokemon> mockPokemonList = new ArrayList<>();
        mockPokemonList.add(new Pokemon());

        Page<Pokemon> mockPage = new PageImpl<>(mockPokemonList);
        when(pokemonRepository.findAll(PageRequest.of(0, 10))).thenReturn(mockPage);

        PokemonPageResponse pageResponse = pokemonService.listPokemons(0, 10);

        assertEquals(mockPage.getContent(), pageResponse.getPokemonList());
    }*/

    @Test
    public void testDeletePokemon() {
        Long pokemonNumber = 1L;
        Pokemon mockPokemon = new Pokemon();
        when(pokemonRepository.findById(pokemonNumber)).thenReturn(Optional.of(mockPokemon));

        boolean result = pokemonService.deletePokemon(pokemonNumber);

        assertTrue(result);
        verify(pokemonRepository, times(1)).delete(mockPokemon);
    }

    @Test
    public void testDeletePokemon_NotFound() {
        Long pokemonNumber = 1L;
        when(pokemonRepository.findById(pokemonNumber)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> pokemonService.deletePokemon(pokemonNumber));
    }

    @Test
    public void testGetPokemonByNumber() {
        Long pokemonNumber = 1L;
        Pokemon mockPokemon = new Pokemon();
        when(pokemonRepository.findById(pokemonNumber)).thenReturn(Optional.of(mockPokemon));

        Pokemon result = pokemonService.getPokemonByNumber(pokemonNumber);

        assertEquals(mockPokemon, result);
    }

    @Test
    public void testGetPokemonByNumber_NotFound() {
        Long pokemonNumber = 1L;
        when(pokemonRepository.findById(pokemonNumber)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> pokemonService.getPokemonByNumber(pokemonNumber));
    }

    @Test
    public void testGetEvolutionsByPokemonNumber() {
        Long pokemonNumber = 1L;
        List<EvolutionDetail> mockEvolutions = Collections.emptyList();
        when(evolutionRepository.findByPokemonNumber(pokemonNumber)).thenReturn(mockEvolutions);

        List<EvolutionDetail> result = pokemonService.getEvolutionsByPokemonNumber(pokemonNumber);

        assertEquals(mockEvolutions, result);
        verify(evolutionRepository, times(1)).findByPokemonNumber(pokemonNumber);
    }
}