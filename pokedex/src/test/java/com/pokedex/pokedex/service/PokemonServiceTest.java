package com.pokedex.pokedex.service;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.repository.EvolutionRepository;
import com.pokedex.pokedex.repository.PokemonRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {
    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private EvolutionRepository evolutionRepository;

    @Mock
    private PokeApiService pokeApiService;

    @Mock
    private PokemonMapper pokemonMapper;

    @InjectMocks
    private PokemonService pokemonService;


    @Test
    public void testAddNewPokemon_Success() {
        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setName(Constant.NAME_BULBASAUR);
        pokemonResponse.setNumber(Constant.NUMBER_BULBASAUR);

        Pokemon pokemonSaved = PokemonMapper.toPokemon(pokemonResponse);
        EvolutionDetail evolutionDetailSaved = new EvolutionDetail();

        when(pokeApiService.getPokemonNameOrNumber(Constant.NAME_BULBASAUR)).thenReturn(pokemonResponse);
        when(pokemonRepository.save(pokemonSaved)).thenReturn(new Pokemon());
        when(evolutionRepository.save(evolutionDetailSaved)).thenReturn(new EvolutionDetail());

        List<EvolutionDetail> evolutionDetails = pokemonService.addNewPokemon(Constant.NAME_BULBASAUR);

        verify(pokeApiService, times(1)).getPokemonNameOrNumber(Constant.NAME_BULBASAUR);
        verify(pokemonRepository, times(2)).save(any());
        verify(evolutionRepository, times(2)).save(any());

        assertEquals(Collections.emptyList(), evolutionDetails);
    }

    @Test
    public void listPokemons_Success(){
        Page<EvolutionDetail> evolutionDetailPage = mock(Page.class);

        when(evolutionRepository.findAll(any(Pageable.class))).thenReturn(evolutionDetailPage);
        when(evolutionDetailPage.getContent()).thenReturn(Collections.singletonList(new EvolutionDetail()));

        PokemonPageResponse pokemonPageResponse = pokemonService.listPokemons(0,10);

        verify(evolutionRepository, times(1)).findAll(any(Pageable.class));

        assertNotNull(pokemonPageResponse);
        assertEquals(1, pokemonPageResponse.getPokemons().size());
    }

    @Test
    public void deletePokemonByNameOrNumber_Success(){
        Pokemon pokemon = new Pokemon();
        when(pokemonRepository.findByNumber(Constant.NUMBER_BULBASAUR)).thenReturn(pokemon);

        verify(pokemonRepository, times(1)).findByNumber(Constant.NUMBER_BULBASAUR);
        verify(evolutionRepository, times(1)).deleteBySelfNumber(Constant.NUMBER_BULBASAUR);
        verify(pokemonRepository, times(1)).delete(pokemon);
    }
}