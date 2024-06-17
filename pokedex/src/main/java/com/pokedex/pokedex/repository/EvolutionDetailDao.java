package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.EvolutionDetail;
import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface EvolutionDetailDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS evolution_details (id SERIAL PRIMARY KEY, pokemon_id INT, min_level INT, trigger_name VARCHAR, evolution_id INT)")
    void createTable();

    @SqlUpdate("INSERT INTO evolution_details (pokemon_id, min_level, trigger_name, evolution_id) VALUES (:self.id, :min_level, :trigger_name, :evolution_id)")
    void insert(@BindBean EvolutionDetail evolutionDetail);

    @SqlQuery("SELECT * FROM evolution_details WHERE id = :id")
    @RegisterBeanMapper(EvolutionDetail.class)
    EvolutionDetail findyById(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM evolution_details")
    @RegisterBeanMapper(EvolutionDetail.class)
    List<EvolutionDetail> findAll();



}
