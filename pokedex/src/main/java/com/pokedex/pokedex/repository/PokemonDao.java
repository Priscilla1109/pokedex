package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.Pokemon;
import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface PokemonDao {
    @SqlUpdate("CREATE TABLE IF NOT EXISTS pokemon (id SERIAL PRIMARY KEY, name VARCHAR, number INT)")
    void createTable();

    @SqlUpdate("INSERT INTO pokemon (name, number) VALUES (:name, :number")
    void insert(@BindBean Pokemon pokemon);

    @SqlQuery("SELECT * FROM pokemon WHERE id = :id")
    @RegisterBeanMapper(Pokemon.class)
    Pokemon findById(@Bind("id") Long id);

    @SqlQuery("SELECT * FROM pokemon")
    @RegisterBeanMapper(Pokemon.class)
    List<Pokemon> findAll();
}
