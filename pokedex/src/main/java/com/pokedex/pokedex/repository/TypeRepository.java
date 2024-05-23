package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.mapper.TypePokemonRowMapper;
import com.pokedex.pokedex.model.TypePokemon;
import java.util.Optional;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

@Repository
@UseClasspathSqlLocator
public interface TypeRepository {
    @SqlUpdate
    @GetGeneratedKeys
    TypePokemon save(@BindBean TypePokemon type);

    @SqlUpdate
    void saveTypePokemon(@Bind("pokemonNumber") Long number, @Bind("type") String type);

    @SqlQuery
    @RegisterRowMapper(TypePokemonRowMapper.class)
    Optional<TypePokemon> findByName(String typeName);
}
