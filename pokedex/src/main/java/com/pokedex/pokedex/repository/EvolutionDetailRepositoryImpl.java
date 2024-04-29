package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.EvolutionApiResponse;
import com.pokedex.pokedex.model.EvolutionDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

//Componente gerenciado pelo spring, permitindo fazer a injeção de dependências
@Component
public class EvolutionDetailRepositoryImpl {
    @Autowired
    JdbcTemplate jdbcTemplate; //injeção de dependência para consultas SQL

    public void populateEvolutionDetails() {
        // Fazer uma requisição HTTP para a API da Pokédex para obter os dados de evolução
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.pokedex.com/evolution";
        EvolutionApiResponse response = restTemplate.getForObject(apiUrl, EvolutionApiResponse.class);

        // Verificar se a resposta da API não é nula e contém dados de evolução
        if (response != null && response.getEvolutions() != null) {
            // Limpar a tabela evolution_details antes de populá-la com os novos dados
            jdbcTemplate.update("DELETE FROM evolution_details");

            // Iterar sobre os dados de evolução e populá-los na tabela evolution_details
            for (EvolutionDetail evolution : response.getEvolutions()) {
                String sql = "INSERT INTO evolution_details (NUMBER, ITEM_NAME, MIN_LEVEL, TRIGGER_NAME) VALUES (?, ?, ?, ?)";
                jdbcTemplate.update(sql,
                        evolution.getNumber(),
                        evolution.getItemName(),
                        evolution.getMinLevel(),
                        evolution.getTriggerName());
            }
        }
    }
}
