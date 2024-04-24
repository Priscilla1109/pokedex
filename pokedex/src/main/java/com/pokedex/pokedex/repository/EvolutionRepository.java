package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.EvolutionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvolutionRepository extends JpaRepository<EvolutionDetail, Long> {
    List<EvolutionDetail> findByPokemonNumber(Long pokemonNumber);
}
