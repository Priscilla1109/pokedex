package com.pokedex.pokedex.mapper;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import com.pokedex.pokedex.config.Constant;
import com.pokedex.pokedex.model.EvolutionDetail;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.jdbi.v3.core.statement.StatementContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
public class EvolutionDetailRowMapperTest {
    private EvolutionDetailRowMapper evolutionDetailRowMapper;

    @Mock
    private ResultSet rs;

    @Mock
    private StatementContext ctx;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        evolutionDetailRowMapper = new EvolutionDetailRowMapper();
    }

    @Test
    public void testMap() throws SQLException {
        when(rs.getLong("pokemon_id")).thenReturn(Constant.NUMBER_BULBASAUR);
        when(rs.getInt("min_level")).thenReturn(Constant.MIN_LEVEL_BULBASAUR);
        when(rs.getString("trigger_name")).thenReturn(Constant.TRIGGER_NAME_BULBASAUR);
        when(rs.getLong("evolution_id")).thenReturn(Constant.NUMBER_IVYSAUR);

        EvolutionDetail evolutionDetail = evolutionDetailRowMapper.map(rs, ctx);

        assertNotNull(evolutionDetail);
        assertEquals(Constant.NUMBER_BULBASAUR, evolutionDetail.getPokemonId());
        assertEquals(Constant.MIN_LEVEL_BULBASAUR, evolutionDetail.getMinLevel());
        assertEquals(Constant.TRIGGER_NAME_BULBASAUR, evolutionDetail.getTriggerName());

        assertNotNull(evolutionDetail.getSelf());
        assertEquals(Constant.NUMBER_BULBASAUR, evolutionDetail.getSelf().getNumber());

        assertNotNull(evolutionDetail.getEvolution());
        assertEquals(Constant.NUMBER_IVYSAUR, evolutionDetail.getEvolution().getNumber());
    }
}
