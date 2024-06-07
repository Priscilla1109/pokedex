package com.pokedex.pokedex.service;

import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;

import com.pokedex.pokedex.repository.EvolutionDetailRepository;
import com.pokedex.pokedex.repository.JdbiEvolutionDetailRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import com.pokedex.pokedex.repository.JdbiTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

//Classe que representa as regras de negócio
@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private PokeApiService pokeApiService;

    @Autowired
    private JdbiTypeRepository jdbiTypeRepository;

    @Autowired
    private EvolutionDetailRepository evolutionDetailRepository;

    public List<EvolutionDetail> addNewPokemon(String nameOrNumber) throws Throwable {
        PokemonResponse pokemonResponse = pokeApiService.getPokemonNameOrNumber(nameOrNumber);
        List<EvolutionDetail> evolutionDetails = PokemonMapper.toDomain(pokemonResponse);

        Pokemon saveSelf = savePokemon(evolutionDetails.get(0).getSelf());
        saveSelf.setType(loadTypeFromDataBase(saveSelf.getNumber()));

        for (EvolutionDetail evolutionDetail : evolutionDetails) {
            Pokemon saveEvolution = savePokemon(evolutionDetail.getEvolution());
            saveEvolution.setType(loadTypeFromDataBase(saveEvolution.getNumber()));
            evolutionDetail.setEvolution(saveEvolution);

            evolutionDetailRepository.save(evolutionDetail);
        }

        return evolutionDetails;
    }

    private List<String> loadTypeFromDataBase(Long pokemonNumber) {
        return jdbiTypeRepository.findByTypePokemonNumber(pokemonNumber);
    }

    private Pokemon savePokemon(Pokemon pokemon) throws Throwable {
        return pokemonRepository.save(pokemon);
    }

    public PokemonPageResponse listPokemons(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<EvolutionDetail> evolutionDetailPage = (Page<EvolutionDetail>) evolutionDetailRepository.findAll(pageable);

        Set<PokemonResponse> pokemonResponses = new HashSet<>();

        for (EvolutionDetail evolutionDetail : evolutionDetailPage.getContent()) {
            Pokemon pokemon = evolutionDetail.getSelf();
            PokemonResponse pokemonResponse = PokemonMapper.toResponse(pokemon);
            List<EvolutionDetail> allEvolutions = evolutionDetailRepository.findBySelfNumber(pokemon.getNumber());
            pokemonResponse.setEvolutions(PokemonMapper.toResponseList(allEvolutions));
            pokemonResponses.add(pokemonResponse);
        }

        return new PokemonPageResponse(
            new ArrayList<>(pokemonResponses),
            new Meta(evolutionDetailPage.getNumber(), evolutionDetailPage.getSize(), (int) evolutionDetailPage.getTotalElements(), evolutionDetailPage.getTotalPages())
        );
    }
//
//    @Transactional //garantir que ele seja executado dentro de uma transação gerenciada pelo Spring
//    public void deletePokemonByNameOrNumber(String nameOrNumber) {
//        Pokemon pokemon = getPokemonByNameOrNumber(nameOrNumber);
//        if (pokemon == null) {
//            throw new PokemonNotFoundException("Pokemon with name or number " + nameOrNumber + " not found.");
//        }
//
//        evolutionRepository.deleteBySelfNumber(pokemon.getNumber());
//
//        pokemonRepository.delete(pokemon);
//    }
//
//    private Pokemon getPokemonByNameOrNumber(String nameOrNumber){
//        try {
//            long number = Long.parseLong(nameOrNumber);
//            return pokemonRepository.findByNumber(number);
//        } catch (NumberFormatException e) {
//            return pokemonRepository.findByName(nameOrNumber);
//        }
//    }
//
//    public Pokemon getPokemonByNumber(Long number) {
//        Optional<Pokemon> pokemon = pokemonRepository.findById(number);
//        return pokemon.orElseThrow(NoSuchElementException::new);
//    }
//
//    public PokemonResponse getEvolutionsByPokemonNumber(Long pokemonNumber){
//        // Verifica se há evoluções para o Pokémon com o número fornecido
//        List<EvolutionDetail> evolutionDetail = evolutionRepository.findBySelf_Number(pokemonNumber);
//        if (evolutionDetail != null | !evolutionDetail.isEmpty()) {
//            return null;
//        }
//
//        EvolutionDetail mainPokemonDetail = evolutionDetail.get(0);
//        Pokemon mainPokemon =mainPokemonDetail.getSelf();
//
//        PokemonResponse pokemonResponse = PokemonMapper.toResponse(mainPokemon);
//        List<PokemonResponse> evolutionresponse = getEvolutionsResponse(evolutionDetail);
//        pokemonResponse.setEvolutions(evolutionresponse);
//
//        return pokemonResponse;
//    }
//
//    private List<PokemonResponse> getEvolutionsResponse(List<EvolutionDetail> evolutionDetails){
//        return evolutionDetails.stream()
//            .map(detail -> PokemonMapper.toResponse(detail.getEvolution()))
//            .collect(Collectors.toList());
//    }
}
