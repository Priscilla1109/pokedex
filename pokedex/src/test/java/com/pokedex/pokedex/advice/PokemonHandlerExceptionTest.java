package com.pokedex.pokedex.advice;


import com.pokedex.pokedex.service.PokemonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PokemonHandlerException.class)
public class PokemonHandlerExceptionTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;  // Supondo que você tenha uma classe PokemonService

    @Test
    public void testPokemonNotFoundException() throws Exception {
        mockMvc.perform(get("/api/pokemon/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Pokemon not found with id: 999"));
    }

    @Test
    public void testBadRequestException() throws Exception {
        mockMvc.perform(post("/api/pokemon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"number\": \"invalid\", \"name\": \"Bulbasaur\", \"imageUrl\": \"https://example.com/image.png\", \"type\": \"Grass\", \"evolutions\": [] }"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Invalid Pokemon number"));
    }

    @Test
    public void testInternalServerErrorException() throws Exception {
        mockMvc.perform(get("/api/pokemon/internalError"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Internal server error occurred"));
    }
}
