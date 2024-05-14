package com.pokedex.pokedex.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokedex.pokedex.mapper.PokemonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
        //TODO: copiar o object mapper da pokeapi
    }
}
