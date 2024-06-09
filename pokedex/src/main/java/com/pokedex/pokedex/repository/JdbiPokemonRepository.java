package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.mapper.PokemonRowMapper;
import com.pokedex.pokedex.model.Pokemon;
import java.util.Optional;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;
import org.springframework.stereotype.Repository;

//Classe que fornece os métodos para acessar o banco de dados
@Repository
@UseClasspathSqlLocator
public interface JdbiPokemonRepository {
    @SqlUpdate
    void insert(@BindBean Pokemon pokemon);

    @SqlUpdate
    void update(@BindBean Pokemon pokemon);

    @SqlQuery //operação de consulta-seleção
    @UseRowMapper(PokemonRowMapper.class)
    Optional<Pokemon> findByNumber(@Bind("number") Long number);

    @SqlQuery
    @UseRowMapper(PokemonRowMapper.class)
    Optional<Pokemon> findByName(@Bind("name") String name);


    @SqlUpdate
    void deletePokemon(@Bind("pokemonNumber") Long pokemonNumber);
}
