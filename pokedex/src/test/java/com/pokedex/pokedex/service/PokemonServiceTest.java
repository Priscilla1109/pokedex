package com.pokedex.pokedex.service;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;
import com.pokedex.pokedex.repository.EvolutionRepository;
import com.pokedex.pokedex.repository.PokemonRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
//@PrepareForTest({PokemonMapper.class}) //Serve para manipular métodos estáticos da classe PokemonMapper
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
    public void testAddNewPokemon() {
        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setNumber(Constant.NUMBER_BULBASAUR);
        pokemonResponse.setName(Constant.NAME_BULBASAUR);
        when(pokeApiService.getPokemonNameOrNumber(anyString())).thenReturn(pokemonResponse);

        // Mock do comportamento do PokemonMapper (usando o mock fornecido)
        List<EvolutionDetail> evolutionDetails = new ArrayList<>();
        when(pokemonMapper.toDomain(any(PokemonResponse.class))).thenReturn(evolutionDetails);

        // Chamar o método a ser testado
        List<EvolutionDetail> result = pokemonService.addNewPokemon(pokemonResponse.getName());

        // Verificar se o método getPokemonNameOrNumber foi chamado corretamente
        verify(pokeApiService).getPokemonNameOrNumber(anyString());

        // Verificar se o método save do pokemonRepository foi chamado para cada EvolutionDetail
        verify(pokemonRepository, times(evolutionDetails.size() * 2)).save(any(Pokemon.class));

        // Verificar se o método save do evolutionRepository foi chamado para cada EvolutionDetail
        verify(evolutionRepository, times(evolutionDetails.size())).save(any(EvolutionDetail.class));

        // Verificar se o resultado retornado é o esperado
        assertEquals(evolutionDetails, result);
    }

    @Test
    public void testListPokemons() {
        int page = 0;
        int pageSize = 10;
        List<Pokemon> pokemonList = new ArrayList<>();

        Pokemon pokemon1 = new Pokemon();
        pokemon1.setNumber(Constant.NUMBER_BULBASAUR);

        // Mock do comportamento do pokemonRepository para retornar uma Page de Pokémon
        Page<Pokemon> pokemonPage = new PageImpl<>(pokemonList);
        when(pokemonRepository.findAll(PageRequest.of(page, pageSize))).thenReturn(pokemonPage);

        // Chamar o método a ser testado
        PokemonPageResponse result = pokemonService.listPokemons(page, pageSize);

        // Verificar se a lista de Pokémon na resposta corresponde à lista de Pokémon retornada pelo repository
        assertEquals(pokemonList.size(), result.getPokemons().size());
        // Verificar se a página na resposta corresponde à página retornada pelo repository
        assertEquals(pokemonPage.getNumber(), result.getMeta().getPage());
        // Verificar se o tamanho da página na resposta corresponde ao tamanho da página retornada pelo repository
        assertEquals(pokemonPage.getSize(), result.getMeta().getPageSize());
        // Verificar se o total de elementos na resposta corresponde ao total de elementos retornado pelo repository
        assertEquals((int) pokemonPage.getTotalElements(), result.getMeta().getPageSize());
        // Verificar se o total de páginas na resposta corresponde ao total de páginas retornado pelo repository
        assertEquals(pokemonPage.getTotalPages(), result.getMeta().getTotalPage());
    }


    /*@Test
    public void testDeletePokemon() {
        Long pokemonNumber = 1L;
        Pokemon mockPokemon = new Pokemon();
        when(pokemonRepository.findById(pokemonNumber)).thenReturn(Optional.of(mockPokemon));

        boolean result = pokemonService.deletePokemonByNameOrNumber(pokemonNumber);

        assertTrue(result);
        verify(pokemonRepository, times(1)).delete(mockPokemon);
    }*/

    /*@Test
    public void testDeletePokemon_NotFound() {
        Long pokemonNumber = 1L;
        when(pokemonRepository.findById(pokemonNumber)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> pokemonService.deletePokemonByNameOrNumber(pokemonNumber));
    }*/

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