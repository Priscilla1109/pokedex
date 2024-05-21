-- Cria as tabelas no momento em que sobe o server

-- Criação da tabela Pokemons
CREATE TABLE IF NOT EXISTS pokemon (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  number BIGINT NOT NULL,
  type VARCHAR(255) NOT NULL,
  imageUrl VARCHAR(255) NOT NULL
);

-- Criação da tabela EvolutionDetail
CREATE TABLE IF NOT EXISTS evolution_detail (
  id INT AUTO_INCREMENT PRIMARY KEY,
  self_id VARCHAR(255) NOT NULL,
  evolution_id VARCHAR(255) NOT NULL,
  minLevel INT NOT NULL,
  triggerName VARCHAR(255) NOT NULL
);