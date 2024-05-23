package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.EvolutionDetail;

import java.util.List;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.locator.UseClasspathSqlLocator;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.springframework.stereotype.Repository;

@Repository
@UseClasspathSqlLocator
public interface EvolutionRepository {

    @SqlUpdate
    @GetGeneratedKeys
    EvolutionDetail save(@BindBean EvolutionDetail evolutionDetail);

}
