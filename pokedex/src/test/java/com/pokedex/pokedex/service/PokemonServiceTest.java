package com.pokedex.pokedex.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.model.EvolutionDetail;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.model.PokemonPageResponse;
import com.pokedex.pokedex.model.PokemonResponse;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {
    /*@Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private EvolutionRepository evolutionRepository;*/

    @Mock
    private PokeApiService pokeApiService;

    @InjectMocks
    private PokemonService pokemonService;

    private Pokemon pokemon;
    private PokemonResponse pokemonResponse;
    private EvolutionDetail evolutionDetail;
    private List<EvolutionDetail> evolutionDetails;

    @BeforeEach
    public void setUp(){
        pokemon= new Pokemon();
        pokemon.setNumber(Constant.NUMBER_BULBASAUR);
        pokemon.setName(Constant.NAME_BULBASAUR);
        pokemon.setType(Constant.TYPE_BULBASAUR);
        pokemon.setImageUrl(Constant.IMAGE_URL_BULBASAUR);

        pokemonResponse = new PokemonResponse();
        pokemonResponse.setNumber(Constant.NUMBER_BULBASAUR);
        pokemonResponse.setName(Constant.NAME_BULBASAUR);
        pokemonResponse.setType(Constant.TYPE_BULBASAUR);
        pokemonResponse.setImageUrl(Constant.IMAGE_URL_BULBASAUR);
        pokemonResponse.setEvolutions(Constant.EVOLUTION_BULBASAUR);

        evolutionDetail = new EvolutionDetail();
        evolutionDetail.setSelf(pokemon);
        evolutionDetail.setMinLevel(Constant.MIN_LEVEL_BULBASAUR);
        evolutionDetail.setTriggerName(Constant.TRIGGER_NAME_BULBASAUR);
        evolutionDetail.setEvolution(new Pokemon());

        evolutionDetails = Collections.singletonList(evolutionDetail);
    }

    /*@Test
    public void testAddNewPokemon_Success() {
        when(pokeApiService.getPokemonNameOrNumber(Constant.NAME_BULBASAUR)).thenReturn(pokemonResponse);
        when(pokemonRepository.save(any(Pokemon.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(evolutionRepository.save(any(EvolutionDetail.class))).thenAnswer(invocation -> invocation.getArgument(0));

        List<EvolutionDetail> result = pokemonService.addNewPokemon(Constant.NAME_BULBASAUR);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(evolutionDetail.getSelf(), result.get(0).getSelf());
        verify(pokeApiService, times(1)).getPokemonNameOrNumber(Constant.NAME_BULBASAUR);
        verify(pokemonRepository, times(2)).save(any(Pokemon.class));
        verify(evolutionRepository, times(1)).save(any(EvolutionDetail.class));
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
    }*/
}