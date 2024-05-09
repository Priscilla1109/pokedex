package com.pokedex.pokedex.service;

import com.pokedex.pokedex.model.EvolutionDetail;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonPageResponse;
import com.pokedex.pokedex.model.PokemonResquest;
import com.pokedex.pokedex.repository.EvolutionRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;
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
        PokemonResquest mockPokemon = new PokemonResquest();

        when(pokemonRepository.save(mockPokemon)).thenReturn(mockPokemon);

        Pokemon addedPokemon = pokemonService.addNewPokemon(mockPokemon);

        assertEquals(mockPokemon, addedPokemon);
        verify(pokemonRepository, times(1)).save(mockPokemon);
    }

    @Test
    public void testListPokemons() {
        List<Pokemon> mockPokemonList = new ArrayList<>();
        mockPokemonList.add(new Pokemon());

        Page<Pokemon> mockPage = new PageImpl<>(mockPokemonList);
        when(pokemonRepository.findAll(PageRequest.of(0, 10))).thenReturn(mockPage);

        PokemonPageResponse pageResponse = pokemonService.listPokemons(0, 10);

        assertEquals(mockPage.getContent(), pageResponse.getPokemonList());
    }

    @Test
    public void testDeletePokemon() {
        Long pokemonNumber = 1L;
        Pokemon mockPokemon = new Pokemon();
        when(pokemonRepository.findById(pokemonNumber)).thenReturn(Optional.of(mockPokemon));

        boolean result = pokemonService.deletePokemonByNameOrNumber(pokemonNumber);

        assertTrue(result);
        verify(pokemonRepository, times(1)).delete(mockPokemon);
    }

    @Test
    public void testDeletePokemon_NotFound() {
        Long pokemonNumber = 1L;
        when(pokemonRepository.findById(pokemonNumber)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> pokemonService.deletePokemonByNameOrNumber(pokemonNumber));
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
        when(evolutionRepository.findBySelf_Number(pokemonNumber)).thenReturn(mockEvolutions);

        List<EvolutionDetail> result = pokemonService.getEvolutionsByPokemonNumber(pokemonNumber);

        assertEquals(mockEvolutions, result);
        verify(evolutionRepository, times(1)).findBySelf_Number(pokemonNumber);
    }
}