SELECT EXISTS (
  SELECT 1 FROM pokemon_type WHERE pokemon_number = :pokemoNumber AND type = :type
);