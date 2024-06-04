INSERT INTO evolution_detail (
  pokemon_id,
  evolution_id,
  min_level,
  trigger_name)
VALUES (
  :self.number,
  :evolution.number,
  :minLevel,
  :triggerName);