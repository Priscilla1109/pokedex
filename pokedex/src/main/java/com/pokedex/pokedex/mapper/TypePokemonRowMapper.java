package com.pokedex.pokedex.mapper;

import com.pokedex.pokedex.model.TypePokemon;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

@Component
public class TypePokemonRowMapper implements RowMapper<TypePokemon> {
    @Override
    public TypePokemon map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new TypePokemon(
            rs.getLong("pokemon_number"),
            rs.getString("type")
        );
    }
}
