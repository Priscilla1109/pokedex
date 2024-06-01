package com.pokedex.pokedex.mapper;

import com.pokedex.pokedex.model.EvolutionDetail;
import com.pokedex.pokedex.model.Pokemon;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.springframework.stereotype.Component;

//Classe usada para mapear os resultados da consulta SQL através do uso do ResultSet para converter em objetos Java
@Component
public class EvolutionDetailRowMapper implements RowMapper<EvolutionDetail>{
    @Override
    public EvolutionDetail map(ResultSet rs, StatementContext ctx) throws SQLException {
        EvolutionDetail evolutionDetail = new EvolutionDetail();
        evolutionDetail.setId(rs.getLong("id"));
        evolutionDetail.setMinLevel(rs.getInt("min_level"));
        evolutionDetail.setTriggerName(rs.getString("trigger_name"));

        Pokemon self = new Pokemon();
        self.setNumber(rs.getLong("number"));
        evolutionDetail.setSelf(self);

        Pokemon evolution = new Pokemon();
        evolution.setNumber(rs.getLong("evolution_id"));
        evolutionDetail.setEvolution(evolution);

        return evolutionDetail;
    }
}