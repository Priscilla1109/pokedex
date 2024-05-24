INSERT INTO evolution_detail (
  self_id,
  evolution_id,
  min_level,
  trigger_name)
VALUES (
  :self.number,
  :evolution.number,
  :minLevel,
  :triggerName);