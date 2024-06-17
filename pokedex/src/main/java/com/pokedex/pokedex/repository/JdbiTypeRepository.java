package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.mapper.TypePokemonRowMapper;
import com.pokedex.pokedex.model.TypePokemon;
import java.util.List;
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
public interface JdbiTypeRepository {
    @SqlUpdate
    @GetGeneratedKeys
    Long save(@BindBean TypePokemon type);

    @SqlUpdate
    void saveTypePokemon(@Bind("pokemonNumber") Long pokemonNumber, @Bind("type") String type);

    @SqlQuery
    @RegisterRowMapper(TypePokemonRowMapper.class)
    Optional<TypePokemon> findByType(@Bind("type") String type);

    @SqlQuery
    List<String> findByTypePokemonNumber(@Bind("pokemonNumber") Long number);

    @SqlUpdate
    void deleteTypePokemon(@Bind("pokemonNumber") Long number, @Bind("type") String type);

    @SqlQuery
    boolean existsTypesPokemon(@Bind("pokemonNumber") Long number, @Bind("type") String type);

    @SqlUpdate
    void deleteAllTypesOfPokemon(@Bind("pokemonNumber") Long pokemonNumber);
}
