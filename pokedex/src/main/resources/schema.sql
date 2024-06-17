-- Cria as tabelas no momento em que sobe o server

-- Criação da tabela Pokemons
CREATE TABLE IF NOT EXISTS POKEMONS (
  name VARCHAR(255) NOT NULL,
  number BIGINT NOT NULL PRIMARY KEY,
  image_url VARCHAR(255) NULL
);

-- Criação da tabela EvolutionDetail
CREATE TABLE IF NOT EXISTS EVOLUTION_DETAIL (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,     -- Identificador único da evolução
  pokemon_id VARCHAR(255) NOT NULL,         -- Identificador do Pokémon que evolui
  evolution_id VARCHAR(255) NOT NULL,       -- Identificador do Pokémon evoluído
  self_number BIGINT NOT NULL,              -- Número do Pokémon que está evoluindo
  trigger_name VARCHAR(255),                -- Tipo de gatilho da evolução
  min_level INT
);

-- Criação da tabela PokemonType
CREATE TABLE IF NOT EXISTS POKEMON_TYPE (
  pokemon_number BIGINT,
  type VARCHAR(255) NOT NULL
);