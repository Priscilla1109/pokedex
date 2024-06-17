package com.pokedex.pokedex.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.EvolutionDetail;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonPageResponse;
import com.pokedex.pokedex.model.PokemonResponse;
import com.pokedex.pokedex.repository.EvolutionDetailRepository;
import com.pokedex.pokedex.repository.JdbiTypeRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import com.pokedex.pokedex.service.PokeApiService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {
    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private PokeApiService pokeApiService;

    @Mock
    private JdbiTypeRepository jdbiTypeRepository;

    @Mock
    private EvolutionDetailRepository evolutionDetailRepository;

    @InjectMocks
    private PokemonService pokemonService;

    @Test
    public void testAddNewPokemon_Success() throws Throwable {
        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setName(Constant.NAME_BULBASAUR);
        pokemonResponse.setNumber(Constant.NUMBER_BULBASAUR);

        PokemonResponse evolution = new PokemonResponse();
        evolution.setNumber(Constant.NUMBER_IVYSAUR);
        evolution.setName(Constant.NAME_IVYSAUR);
        pokemonResponse.setEvolutions(List.of(evolution));

        Pokemon bulbasaur = new Pokemon(Constant.NUMBER_BULBASAUR, Constant.NAME_BULBASAUR, Constant.IMAGE_URL_BULBASAUR, Constant.TYPE_BULBASAUR);
        Pokemon ivysaur = new Pokemon(Constant.NUMBER_IVYSAUR, Constant.NAME_IVYSAUR, Constant.IMAGE_URL_BULBASAUR, Constant.TYPE_BULBASAUR);

        EvolutionDetail evolutionDetail = new EvolutionDetail();
        evolutionDetail.setSelf(bulbasaur);
        evolutionDetail.setEvolution(ivysaur);
        List<EvolutionDetail> evolutionDetails = new ArrayList<>();
        evolutionDetails.add(evolutionDetail);

        when(pokeApiService.getPokemonNameOrNumber(Constant.NAME_BULBASAUR)).thenReturn(pokemonResponse);
        when(pokemonRepository.save(any(Pokemon.class))).thenReturn(bulbasaur, ivysaur);

        List<EvolutionDetail> result = pokemonService.addNewPokemon(Constant.NAME_BULBASAUR);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(Constant.NAME_BULBASAUR, result.get(0).getSelf().getName());
        assertEquals(Constant.NAME_IVYSAUR, result.get(0).getEvolution().getName());

        verify(pokemonRepository, times(1)).save(bulbasaur);
    }

    @Test
    public void testListPokemons() {
        int page = 0;
        int pageSize = 10;
        List<EvolutionDetail> evolutionDetails = new ArrayList<>();

        when(evolutionDetailRepository.findAll(pageSize, page * pageSize)).thenReturn(evolutionDetails);
        when(evolutionDetailRepository.countAll()).thenReturn(10);

        PokemonPageResponse response = pokemonService.listPokemons(page, pageSize);

        assertNotNull(response);
        assertEquals(1, response.getMeta().getTotalElements()); //----- Está invertido??
        assertEquals(10L, response.getMeta().getTotalPage());
    }

    @Test
    public void testDeletePokemonByNameOrNumber() {
        Pokemon mockPokemon = new Pokemon();
        mockPokemon.setName(Constant.NAME_BULBASAUR);
        mockPokemon.setNumber(Constant.NUMBER_BULBASAUR);

        when(pokemonRepository.getPokemonByNameOrNumber(mockPokemon.getName())).thenReturn(Optional.of(mockPokemon));

        pokemonService.deletePokemonByNameOrNumber(mockPokemon.getName());

        verify(evolutionDetailRepository, times(1)).deleteBySelfNumber(mockPokemon.getNumber());
        verify(pokemonRepository, times(1)).deletePokemon(mockPokemon.getNumber());
    }

    @Test
    public void testDeletePokemonByNameOrNumber_NotFound() {
        String nameOrNumber = "XXXXXX";

        when(pokemonRepository.getPokemonByNameOrNumber(nameOrNumber)).thenReturn(Optional.empty());

        assertThrows(PokemonNotFoundException.class, () -> {
            pokemonService.deletePokemonByNameOrNumber(nameOrNumber);
        });
    }

    @Test
    public void testGetEvolutionsByPokemonNumber() {
        Pokemon pokemonSelf = new Pokemon(Constant.NUMBER_BULBASAUR, Constant.NAME_BULBASAUR, Constant.IMAGE_URL_BULBASAUR, Constant.TYPE_BULBASAUR);
        Pokemon pokemonEvolution = new Pokemon(Constant.NUMBER_IVYSAUR, Constant.NAME_IVYSAUR, Constant.IMAGE_URL_BULBASAUR, Constant.TYPE_BULBASAUR);

        EvolutionDetail selfEvolutionDetail = new EvolutionDetail();
        selfEvolutionDetail.setSelf(pokemonSelf);
        selfEvolutionDetail.setEvolution(pokemonEvolution);

        List<EvolutionDetail> evolutionDetails = List.of(selfEvolutionDetail);

        when(evolutionDetailRepository.findBySelfNumber(Constant.NUMBER_BULBASAUR)).thenReturn(evolutionDetails);

        PokemonResponse response = PokemonMapper.toResponse(pokemonSelf);
        List<PokemonResponse> listEvolutions = pokemonService.getEvolutionsResponse(evolutionDetails);
        response.setEvolutions(listEvolutions);
        pokemonService.getEvolutionsByPokemonNumber(response.getNumber());

        assertNotNull(response);
        assertEquals(pokemonSelf.getNumber(), response.getNumber());
        assertEquals(pokemonSelf.getName(), response.getName());
        assertEquals(pokemonSelf.getImageUrl(), response.getImageUrl());
        assertEquals(pokemonSelf.getType(), response.getType());

        assertEquals(pokemonEvolution.getNumber(), response.getEvolutions().get(0).getNumber());
        assertEquals(pokemonEvolution.getName(), response.getEvolutions().get(0).getName());
        assertEquals(pokemonEvolution.getImageUrl(), response.getEvolutions().get(0).getImageUrl());
        assertEquals(pokemonEvolution.getType(), response.getEvolutions().get(0).getType());

        verify(evolutionDetailRepository, times(1)).findBySelfNumber(Constant.NUMBER_BULBASAUR);
    }

    @Test
    public void testGetEvolutionsByPokemonNumber_NoEvolutions() {
        when(evolutionDetailRepository.findBySelfNumber(Constant.NUMBER_BULBASAUR)).thenReturn(Collections.emptyList());

        PokemonResponse response = pokemonService.getEvolutionsByPokemonNumber(Constant.NUMBER_BULBASAUR);

        assertNull(response);
    }
}