package com.pokedex.pokedex.service;

import com.pokedex.pokedex.exception.PokemonNotFoundException;
import com.pokedex.pokedex.mapper.PokemonMapper;
import com.pokedex.pokedex.model.*;

import com.pokedex.pokedex.repository.EvolutionDetailRepository;
import com.pokedex.pokedex.repository.PokemonRepository;
import com.pokedex.pokedex.repository.JdbiTypeRepository;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<String> loadTypeFromDataBase(Long pokemonNumber) {
        return jdbiTypeRepository.findByTypePokemonNumber(pokemonNumber);
    }

    public Pokemon savePokemon(Pokemon pokemon) throws Throwable {
        return pokemonRepository.save(pokemon);
    }

    public PokemonPageResponse listPokemons(int page, int pageSize) {
        int offset = page * pageSize;

        List<EvolutionDetail> evolutionDetails = evolutionDetailRepository.findAll(pageSize, offset);
        long totalElements = evolutionDetailRepository.countAll();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        Set<PokemonResponse> pokemonResponses = new HashSet<>();

        for (EvolutionDetail evolutionDetail : evolutionDetails) {
            Pokemon pokemon = evolutionDetail.getSelf();
            PokemonResponse pokemonResponse = PokemonMapper.toResponse(pokemon);

            // Obter os tipos do Pokémon
            List<String> pokemonTypes = getPokemonTypes(pokemon.getNumber());
            pokemonResponse.setType(pokemonTypes);

            List<EvolutionDetail> allEvolutions = evolutionDetailRepository.findBySelfNumber(pokemon.getNumber());
            pokemonResponse.setEvolutions(PokemonMapper.toResponseList(allEvolutions));
            pokemonResponses.add(pokemonResponse);
        }

        return new PokemonPageResponse(
                new ArrayList<>(pokemonResponses),
                new Meta(page, pageSize, (int) totalElements, totalPages)
        );
    }

    private List<String> getPokemonTypes(Long pokemonNumber) {
        return jdbiTypeRepository.findByTypePokemonNumber(pokemonNumber);
    }

    public void deletePokemonByNameOrNumber(String nameOrNumber) {
        Optional<Pokemon> pokemonOptional = pokemonRepository.getPokemonByNameOrNumber(nameOrNumber);

        // Verifica se o Pokémon foi encontrado
        if (pokemonOptional.isPresent()) {
            Pokemon pokemon = pokemonOptional.get();

            // Deleta os detalhes de evolução associados ao Pokémon
            evolutionDetailRepository.deleteBySelfNumber(pokemon.getNumber());

            // Deleta o Pokémon do banco de dados
            pokemonRepository.deletePokemon(pokemon.getNumber());
        } else {
            throw new PokemonNotFoundException("Pokemon with name or number " + nameOrNumber + " not found.");
        }
    }


    public PokemonResponse getEvolutionsByPokemonNumber(Long pokemonNumber){
        // Verifica se há evoluções para o Pokémon com o número fornecido
        List<EvolutionDetail> evolutionDetail = evolutionDetailRepository.findBySelfNumber(pokemonNumber);
        if (evolutionDetail == null || evolutionDetail.isEmpty()) {
            return null;
        }

        EvolutionDetail mainPokemonDetail = evolutionDetail.get(0);
        Pokemon mainPokemon = mainPokemonDetail.getSelf();

        PokemonResponse pokemonResponse = PokemonMapper.toResponse(mainPokemon);
        List<PokemonResponse> evolutionsResponse = getEvolutionsResponse(evolutionDetail);
        pokemonResponse.setEvolutions(evolutionsResponse);

        return pokemonResponse;
    }

    public List<PokemonResponse> getEvolutionsResponse(List<EvolutionDetail> evolutionDetails){
        return evolutionDetails.stream()
            .map(detail -> PokemonMapper.toResponse(detail.getEvolution()))
            //.filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
}
