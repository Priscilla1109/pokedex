package com.pokedex.pokedex.mapper;

import com.pokedex.pokedex.model.Pokemon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

//Classe usada para mapear os resultados da consulta SQL através do uso do ResultSet para converter em objetos Java
@Component
public class PokemonRowMapper implements RowMapper<Pokemon> {
    @Override
    public Pokemon map(ResultSet rs, StatementContext ctx) throws SQLException {
        Map<Long, Pokemon> pokemonMap = new HashMap<>();

        while (rs.next()) {
            Long number = rs.getLong("number");
            Pokemon pokemon = pokemonMap.computeIfAbsent(number, k -> {
                try {
                    Pokemon p = new Pokemon();
                    p.setNumber(rs.getLong("number"));
                    p.setName(rs.getString("name"));
                    p.setImageUrl(rs.getString("image_url"));
                    p.setType(new ArrayList<>());
                    return p;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            String type = rs.getString("type");
            if (type != null) {
                pokemon.getType().add(type);
            }
        }
        return pokemonMap.isEmpty() ? null : pokemonMap.values().iterator().next();
    }
}