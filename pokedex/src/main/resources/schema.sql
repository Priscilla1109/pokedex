-- Cria as tabelas no momento em que sobe o server

-- Criação da tabela Pokemons
CREATE TABLE IF NOT EXISTS POKEMONS (
  name VARCHAR(255) NOT NULL,
  number BIGINT NOT NULL PRIMARY KEY,
  image_url VARCHAR(255) NULL
);

-- Criação da tabela EvolutionDetail
CREATE TABLE IF NOT EXISTS EVOLUTION_DETAIL (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  pokemon_id VARCHAR(255) NOT NULL,
  evolution_id VARCHAR(255) NOT NULL,
  trigger_name VARCHAR(255),
  min_level INT
);

-- Criação da tabela PokemonType
CREATE TABLE IF NOT EXISTS POKEMON_TYPE (
  pokemon_number BIGINT,
  type VARCHAR(255) NOT NULL
);