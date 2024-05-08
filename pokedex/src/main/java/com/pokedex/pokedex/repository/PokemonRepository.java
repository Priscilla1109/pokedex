package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//Classe que fornece os métodos para acessar o banco de dados
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    Pokemon findByName(String name);
    Pokemon findByNumber(Long number);

}
