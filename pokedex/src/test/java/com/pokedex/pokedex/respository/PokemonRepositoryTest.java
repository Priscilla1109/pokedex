package com.pokedex.pokedex.respository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.repository.JdbiPokemonRepository;
import com.pokedex.pokedex.repository.JdbiTypeRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PokemonRepositoryTest {
    @Mock
    private JdbiPokemonRepository jdbiPokemonRepository;

    @Mock
    private JdbiTypeRepository jdbiTypeRepository;

    @InjectMocks
    private PokemonRepository pokemonRepository;

    private Pokemon pokemon;

    @BeforeEach
    void setUp(){
        pokemon = new Pokemon();
        pokemon.setName(Constant.NAME_BULBASAUR);
        pokemon.setNumber(Constant.NUMBER_BULBASAUR);
        pokemon.setType(Constant.TYPE_BULBASAUR);
    }

    @Test
    public void testSave_NewPokemon() throws Throwable {
        when(jdbiPokemonRepository.findByNumber(pokemon.getNumber()))
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(pokemon));
        doNothing().when(jdbiPokemonRepository).insert(pokemon);
        doNothing().when(jdbiTypeRepository).saveTypePokemon(pokemon.getNumber(),"Grass");
        doNothing().when(jdbiTypeRepository).saveTypePokemon(pokemon.getNumber(),"Poison");
        pokemonRepository.save(pokemon);

        verify(jdbiPokemonRepository, times(1)).insert(pokemon);
        verify(jdbiTypeRepository, times(1)).saveTypePokemon(pokemon.getNumber(), "Grass");
        verify(jdbiTypeRepository, times(1)).saveTypePokemon(pokemon.getNumber(), "Poison");
    }

    @Test
    public void testSave_ExistingPokemon() throws Throwable {
        when(jdbiPokemonRepository.findByNumber(pokemon.getNumber()))
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(pokemon));

        Pokemon result = pokemonRepository.save(pokemon);

        assertNotNull(result);
        verify(jdbiPokemonRepository, times(2)).findByNumber(pokemon.getNumber());
        verify(jdbiPokemonRepository, times(1)).insert(pokemon);
    }

    @Test
    public void testUpdate_Pokemon() {
        when(jdbiTypeRepository.findByTypePokemonNumber(pokemon.getNumber()))
            .thenReturn(List.of("Grass"));

        doNothing().when(jdbiPokemonRepository).update(pokemon);

        pokemonRepository.update(pokemon);

        verify(jdbiPokemonRepository, times(1)).update(pokemon);
    }

//    @Test
//    public void testUpdate_AddAndRemoveTypes() {
//        when(jdbiTypeRepository.findByTypePokemonNumber(pokemon.getNumber()))
//            .thenReturn(Constant.TYPE_BULBASAUR);
//
//        when(jdbiTypeRepository.existsTypesPokemon(pokemon.getNumber(), "Poison"))
//            .thenReturn(false);
//
//        pokemonRepository.update(pokemon);
//
//        verify(jdbiTypeRepository, times(1)).saveTypePokemon(pokemon.getNumber(),"Poison");
//    }

    @Test
    public void testInsertPokemon() {
        doNothing().when(jdbiPokemonRepository).insert(pokemon);

        pokemonRepository.insert(pokemon);

        verify(jdbiPokemonRepository, times(1)).insert(pokemon);
        verify(jdbiTypeRepository, times(1)).saveTypePokemon(pokemon.getNumber(), "Grass");
        verify(jdbiTypeRepository, times(1)).saveTypePokemon(pokemon.getNumber(), "Poison");
    }

    @Test
    public void testDeletePokemon() {
        doNothing().when(jdbiPokemonRepository).deletePokemon(pokemon.getNumber());

        pokemonRepository.deletePokemon(pokemon.getNumber());

        verify(jdbiPokemonRepository, times(1)).deletePokemon(pokemon.getNumber());
    }

    @Test
    public void testGetPokemonByNumber() {
        when(jdbiPokemonRepository.findByNumber(pokemon.getNumber()))
            .thenReturn(Optional.of(pokemon));

        Optional<Pokemon> result = pokemonRepository.getPokemonByNameOrNumber("1");

        assertTrue(result.isPresent());
        assertEquals(result, Optional.of(pokemon));
        verify(jdbiPokemonRepository, times(1)).findByNumber(pokemon.getNumber());
    }

    @Test
    public void testGetPokemonByName() {
        when(jdbiPokemonRepository.findByName(pokemon.getName()))
            .thenReturn(Optional.of(pokemon));

        Optional<Pokemon> result = pokemonRepository.getPokemonByNameOrNumber(Constant.NAME_BULBASAUR);

        assertTrue(result.isPresent());
        assertEquals(result, Optional.of(pokemon));
        verify(jdbiPokemonRepository, times(1)).findByName(pokemon.getName());
    }
}
