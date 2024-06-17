package com.pokedex.pokedex.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.model.TypePokemon;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TypePokemonRowMapperTest {
    private TypePokemonRowMapper typePokemonRowMapper;

    @Mock
    private ResultSet rs;

    @Mock
    private StatementContext ctx;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        typePokemonRowMapper = new TypePokemonRowMapper();
    }

    @Test
    public void testMap() throws SQLException {
        when(rs.getLong("pokemon_number")).thenReturn(Constant.NUMBER_BULBASAUR);
        when(rs.getString("type")).thenReturn("Grass");

        TypePokemon typePokemon = typePokemonRowMapper.map(rs, ctx);

        assertEquals(Constant.NUMBER_BULBASAUR, typePokemon.getPokemonNumber());
        assertEquals("Grass", typePokemon.getType());
    }
}
