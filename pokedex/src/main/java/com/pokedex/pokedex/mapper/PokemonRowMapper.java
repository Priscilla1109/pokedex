package com.pokedex.pokedex.mapper;

import com.pokedex.pokedex.model.Pokemon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

//Classe usada para mapear os resultados da consulta SQL através do uso do ResultSet para converter em objetos Java
@Component
public class PokemonRowMapper implements RowMapper<Pokemon> {
    @Override
    public Pokemon map(ResultSet rs, StatementContext ctx) throws SQLException {
        Pokemon p = new Pokemon();
        p.setNumber(rs.getLong("number"));
        p.setName(rs.getString("name"));
        p.setImageUrl(rs.getString("image_url"));
        p.setType(new ArrayList<>());
        return p;
    }
}