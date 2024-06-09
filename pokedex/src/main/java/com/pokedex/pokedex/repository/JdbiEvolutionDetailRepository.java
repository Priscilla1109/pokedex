package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.mapper.EvolutionDetailRowMapper;
import com.pokedex.pokedex.model.EvolutionDetail;

import com.pokedex.pokedex.model.Pokemon;
import java.util.List;
import java.util.Optional;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@UseClasspathSqlLocator
public interface JdbiEvolutionDetailRepository {
    @SqlUpdate
    @GetGeneratedKeys
    Long save(@BindBean EvolutionDetail evolutionDetail);

    @SqlQuery
    @RegisterRowMapper(EvolutionDetailRowMapper.class)
    List<EvolutionDetail> findAll(@Bind("limit") int limit, @Bind("offset") int offset);

    @SqlQuery
    @RegisterRowMapper(EvolutionDetailRowMapper.class)
    List<EvolutionDetail> findBySelfNumber(@Bind("id") Long id);

    @SqlQuery
    @RegisterRowMapper(EvolutionDetailRowMapper.class)
    Optional<EvolutionDetail> findByPokemonAndEvolution(@Bind("pokemonId") Long pokemonId, @Bind("evolutionId") Long evolutionId);

    @SqlUpdate
    void update(@BindBean EvolutionDetail evolutionDetail);

    @SqlQuery
    int countAll();
}
