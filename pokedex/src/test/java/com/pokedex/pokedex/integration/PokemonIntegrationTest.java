package com.pokedex.pokedex.integration;

import com.pokedex.pokedex.config.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PokemonIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testGetPokemonByName(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://localhost:" + port + "/APIs/pokedex/pokemon/bulbasaur", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), Constant.NAME_BULBASAUR);
    }

    @Test
    public void testGetListPokemons(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://localhost:" + port + "/APIs/pokedex/pokemon", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody(), Constant.NAME_BULBASAUR);
    }

    @Test
    public void testGetEvolutionChainByName(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://localhost:" + port + "/APIs/pokedex/evolution-chain/bulbasaur", String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("ivysaur"));
        assertTrue(responseEntity.getBody().contains("venusaur"));
    }

    //Teste de erro: pokemon não encontrado
    @Test
    public void testGetPokemonByNameNotFound(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://localhost:" + port + "/APIs/pokedex/pokemon/unknown", String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Pokemon not found with name: unknown"));
    }

    //Teste de erro: cadeia de evolução não encontrada
    @Test
    public void testGetEvolutionChainByNameNotFound(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://localhost:" + port + "/APIs/pokedex/evolution-chain/unknown", String.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().contains("Pokemon not found with name: unknown"));
    }
}
