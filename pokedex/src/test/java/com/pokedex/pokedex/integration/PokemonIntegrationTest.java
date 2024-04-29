package com.pokedex.pokedex.integration;

import com.pokedex.pokedex.config.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PokemonIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetPokemonByNameOrNumber() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/APIs/api-pokedex/pokemon/bulbasaur")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("bulbasaur"));
    }

    @Test
    public void testGetSpeciesByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/APIs/api-pokedex/pokemon-species/bulbasaur")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("bulbasaur"));
    }

    @Test
    public void testGetEvolutionChainById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/APIs/api-pokedex/evolution-chain/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}

