package com.pokedex.pokedex.config;

import com.pokedex.pokedex.repository.EvolutionDetailRepository;
import com.pokedex.pokedex.repository.JdbiPokemonRepository;
import com.pokedex.pokedex.repository.TypeRepository;
import java.util.List;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.spi.JdbiPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
public class JdbiConfing {

    @Bean
    public Jdbi jdbi(DataSource ds, List<JdbiPlugin> jdbiPlugins, List<RowMapper<?>> rowMappers) {
        TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(ds); //uso de um DataSource disponível e agrupando-o em TransactionAwareDataSourceProxy
        Jdbi jdbi = Jdbi.create(proxy);

        // Register all available plugins
        jdbiPlugins.forEach(jdbi::installPlugin);

        // Register all available rowMappers
        rowMappers.forEach(jdbi::registerRowMapper);
        return jdbi;
    }

    @Bean
    public JdbiPlugin sqlObjectPlugin() {
        return new SqlObjectPlugin();
    }

    @Bean
    public JdbiPokemonRepository jdbiPokemonRepository(Jdbi jdbi){
        return jdbi.onDemand(JdbiPokemonRepository.class);
    }

    @Bean
    public EvolutionDetailRepository evolutionRepository(Jdbi jdbi){
        return jdbi.onDemand(EvolutionDetailRepository.class);
    }

    @Bean
    public TypeRepository typeRepository(Jdbi jdbi){
        return jdbi.onDemand(TypeRepository.class);
    }
}