package com.pokedex.pokedex.respository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.model.EvolutionDetail;
import com.pokedex.pokedex.model.Pokemon;
import com.pokedex.pokedex.repository.EvolutionDetailRepository;
import com.pokedex.pokedex.repository.JdbiEvolutionDetailRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EvolutionDetailsRepositoryTest {
    @Mock
    private JdbiEvolutionDetailRepository jdbiEvolutionDetailRepository;

    @InjectMocks
    private EvolutionDetailRepository evolutionDetailRepository;

    @Test
    public void testSave_NewEvolution() throws Throwable {
        EvolutionDetail evolutionDetail = new EvolutionDetail();

        Pokemon pokemonSelf = new Pokemon();
        pokemonSelf.setName(Constant.NAME_BULBASAUR);
        pokemonSelf.setNumber(Constant.NUMBER_BULBASAUR);
        evolutionDetail.setSelf(pokemonSelf);

        Pokemon pokemonEvolution = new Pokemon();
        pokemonEvolution.setNumber(Constant.NUMBER_IVYSAUR);
        pokemonEvolution.setName(Constant.NAME_IVYSAUR);
        evolutionDetail.setEvolution(pokemonEvolution);

        when(jdbiEvolutionDetailRepository.findByPokemonAndEvolution(pokemonSelf.getNumber(), pokemonEvolution.getNumber()))
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(evolutionDetail));

        when(jdbiEvolutionDetailRepository.save(evolutionDetail)).thenReturn(evolutionDetail.getPokemonId());

        EvolutionDetail savedEvolution = evolutionDetailRepository.save(evolutionDetail);

        verify(jdbiEvolutionDetailRepository, times(2)).findByPokemonAndEvolution(pokemonSelf.getNumber(), pokemonEvolution.getNumber());
        verify(jdbiEvolutionDetailRepository, times(1)).save(evolutionDetail);
        assertEquals(evolutionDetail, savedEvolution);
    }

//    @Test(expected = RuntimeException.class)
//    public void testSave_ThrowsException() throws Throwable {
//        EvolutionDetail evolutionDetail = new EvolutionDetail();
//
//        Pokemon pokemonSelf = new Pokemon();
//        pokemonSelf.setName(Constant.NAME_BULBASAUR);
//        pokemonSelf.setNumber(Constant.NUMBER_BULBASAUR);
//        evolutionDetail.setSelf(pokemonSelf);
//
//        Pokemon pokemonEvolution = new Pokemon();
//        pokemonEvolution.setNumber(Constant.NUMBER_IVYSAUR);
//        pokemonEvolution.setName(Constant.NAME_IVYSAUR);
//        evolutionDetail.setEvolution(pokemonEvolution);
//
//        when(jdbiEvolutionDetailRepository.findByPokemonAndEvolution(pokemonSelf.getNumber(), pokemonEvolution.getNumber()))
//            .thenReturn(Optional.empty());
//
//        when(jdbiEvolutionDetailRepository.save(evolutionDetail)).thenReturn(evolutionDetail.getPokemonId());
//
//        when(jdbiEvolutionDetailRepository.findByPokemonAndEvolution(pokemonSelf.getNumber(), pokemonEvolution.getNumber()))
//            .thenReturn(Optional.empty());
//
//        EvolutionDetail savedEvolution = evolutionDetailRepository.save(evolutionDetail);
//
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//            evolutionDetailRepository.save(evolutionDetail);
//        });
//
//        assertEquals("Failed to save or update the EvolutionDetail", exception.getMessage());
//    }

    @Test
    public void testUpdate_ExistingEvolution() throws Throwable {
        EvolutionDetail evolutionDetail = new EvolutionDetail();
        Pokemon pokemonSelf = new Pokemon();
        pokemonSelf.setName(Constant.NAME_BULBASAUR);
        pokemonSelf.setNumber(Constant.NUMBER_BULBASAUR);
        evolutionDetail.setSelf(pokemonSelf);

        Pokemon pokemonEvolution = new Pokemon();
        pokemonEvolution.setNumber(Constant.NUMBER_IVYSAUR);
        pokemonEvolution.setName(Constant.NAME_IVYSAUR);
        evolutionDetail.setEvolution(pokemonEvolution);

        when(jdbiEvolutionDetailRepository.findByPokemonAndEvolution(pokemonSelf.getNumber(), pokemonEvolution.getNumber()))
            .thenReturn(Optional.of(evolutionDetail));
        doNothing().when(jdbiEvolutionDetailRepository).update(evolutionDetail);
        when(jdbiEvolutionDetailRepository.findByPokemonAndEvolution(pokemonSelf.getNumber(), pokemonEvolution.getNumber()))
            .thenReturn(Optional.of(evolutionDetail));

        EvolutionDetail savedEvolutionDetail = evolutionDetailRepository.save(evolutionDetail);

        verify(jdbiEvolutionDetailRepository, times(2)).findByPokemonAndEvolution(pokemonSelf.getNumber(), pokemonEvolution.getNumber());
        verify(jdbiEvolutionDetailRepository, times(0)).save(evolutionDetail);
        verify(jdbiEvolutionDetailRepository, times(1)).update(evolutionDetail);
        assertEquals(evolutionDetail, savedEvolutionDetail);
    }

    @Test
    public void testFindAll() {
        List<EvolutionDetail> evolutionDetails = new ArrayList<>();
        when(jdbiEvolutionDetailRepository.findAll(anyInt(), anyInt())).thenReturn(evolutionDetails);

        List<EvolutionDetail> result = evolutionDetailRepository.findAll(10, 0 );

        verify(jdbiEvolutionDetailRepository, times(1)).findAll(10, 0);
        assertEquals(evolutionDetails, result);
    }

    @Test
    public void testFindBySelfNumber() {
        EvolutionDetail evolutionDetail = new EvolutionDetail();
        Pokemon pokemonSelf = new Pokemon();
        pokemonSelf.setName(Constant.NAME_BULBASAUR);
        pokemonSelf.setNumber(Constant.NUMBER_BULBASAUR);
        evolutionDetail.setSelf(pokemonSelf);

        Pokemon pokemonEvolution = new Pokemon();
        pokemonEvolution.setNumber(Constant.NUMBER_IVYSAUR);
        pokemonEvolution.setName(Constant.NAME_IVYSAUR);
        evolutionDetail.setEvolution(pokemonEvolution);

        List<EvolutionDetail> evolutionDetails = Arrays.asList(evolutionDetail);

        when(jdbiEvolutionDetailRepository.findBySelfNumber(Constant.NUMBER_BULBASAUR)).thenReturn(evolutionDetails);

        List<EvolutionDetail> result = evolutionDetailRepository.findBySelfNumber(Constant.NUMBER_BULBASAUR);

        verify(jdbiEvolutionDetailRepository, times(1)).findBySelfNumber(Constant.NUMBER_BULBASAUR);
        assertEquals(evolutionDetails, result);
    }

    @Test
    public void testCountAll() {
        when(jdbiEvolutionDetailRepository.countAll()).thenReturn(100);

        int result = evolutionDetailRepository.countAll();

        verify(jdbiEvolutionDetailRepository, times(1)).countAll();
        assertEquals(100, result);
    }

    @Test
    public void testDeleteBySelfNumber() {
        doNothing().when(jdbiEvolutionDetailRepository).deleteBySelfNumber(Constant.NUMBER_BULBASAUR);

        evolutionDetailRepository.deleteBySelfNumber(Constant.NUMBER_BULBASAUR);

        verify(jdbiEvolutionDetailRepository, times(1)).deleteBySelfNumber(Constant.NUMBER_BULBASAUR);
    }
}
