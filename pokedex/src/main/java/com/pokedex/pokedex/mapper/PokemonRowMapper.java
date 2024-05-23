package com.pokedex.pokedex.mapper;

import com.pokedex.pokedex.model.Pokemon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

//Classe usada para mapear os resultados da consulta SQL através do uso do ResultSet para converter em objetos Java
public class PokemonRowMapper implements RowMapper<Pokemon> {
    @Override
    public Pokemon map(ResultSet rs, StatementContext ctx) throws SQLException {
        Pokemon pokemon = new Pokemon();
        pokemon.setNumber(rs.getLong("number"));
        pokemon.setName(rs.getString("name"));
        pokemon.setImageUrl(rs.getString("imageUrl"));

        return pokemon;
    }

}