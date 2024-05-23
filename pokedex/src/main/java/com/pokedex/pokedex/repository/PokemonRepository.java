package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.mapper.PokemonRowMapper;
import com.pokedex.pokedex.model.Pokemon;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

//Classe que fornece os métodos para acessar o banco de dados
@Repository
@UseClasspathSqlLocator
public interface PokemonRepository {
    @SqlUpdate
    @GetGeneratedKeys
    Pokemon save(@BindBean Pokemon pokemon);

    /*@SqlQuery //operação de consulta-seleção
    @RegisterRowMapper(PokemonRowMapper.class)
    Pokemon findByNumber(@Bind("number") Long number);

    @SqlQuery
    @RegisterRowMapper(PokemonRowMapper.class)
    Pokemon findByName(@Bind("name") String name);*/
}
