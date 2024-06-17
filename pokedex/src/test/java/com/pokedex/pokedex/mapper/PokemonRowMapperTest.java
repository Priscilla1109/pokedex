package com.pokedex.pokedex.mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.model.Pokemon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PokemonRowMapperTest {
    private PokemonRowMapper pokemonRowMapper;

    @Mock
    private ResultSet rs;

    @Mock
    private StatementContext ctx;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        pokemonRowMapper = new PokemonRowMapper();
    }

    @Test
    public void testMap() throws SQLException {
        when(rs.getLong("number")).thenReturn(Constant.NUMBER_BULBASAUR);
        when(rs.getString("name")).thenReturn(Constant.NAME_BULBASAUR);
        when(rs.getString("image_url")).thenReturn(Constant.IMAGE_URL_BULBASAUR);

        Pokemon pokemon = pokemonRowMapper.map(rs, ctx);

        assertEquals(Constant.NUMBER_BULBASAUR, pokemon.getNumber());
        assertEquals(Constant.NAME_BULBASAUR, pokemon.getName());
        assertEquals(Constant.IMAGE_URL_BULBASAUR, pokemon.getImageUrl());
        assertEquals(new ArrayList<>(), pokemon.getType());
    }
}
