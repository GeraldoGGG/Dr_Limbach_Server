-- Create a GIN index on the title column
CREATE INDEX IF NOT EXISTS idx_event_title_trgm
ON event
USING gin (title gin_trgm_ops);