-- Create Table: analysis_details
CREATE TABLE analysis_detail (
                                  id BIGSERIAL PRIMARY KEY,
                                  version BIGINT,
                                  string_value VARCHAR(500),  -- Increased size to 500 characters
                                  key_value VARCHAR(500),     -- Increased size to 500 characters
                                  analysis_id BIGINT NOT NULL,
                                  FOREIGN KEY (analysis_id) REFERENCES analysis(id) ON DELETE CASCADE
);
-- Create the sequence with increment size of 50 (matching Hibernate allocationSize)
CREATE SEQUENCE analysis_detail_seq START WITH 1 INCREMENT BY 1;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE analysis_detail_seq OWNED BY analysis_detail.id;

-- Index for faster lookups
CREATE INDEX idx_analysis_detail_analysis_id ON analysis_detail (analysis_id);
