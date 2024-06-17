INSERT INTO evolution_detail (
  pokemon_id,
  evolution_id,
  self_number,
  min_level,
  trigger_name
)
VALUES (
  :self.number,
  :evolution.number,
  :self.number,
  :minLevel,
  :triggerName
);