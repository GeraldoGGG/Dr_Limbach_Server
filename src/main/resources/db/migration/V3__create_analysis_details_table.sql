-- Create Table: analysis_details
CREATE TABLE analysis_details (
                                  id BIGSERIAL PRIMARY KEY,
                                  version BIGINT,
                                  string_value VARCHAR(500),  -- Increased size to 500 characters
                                  key_value VARCHAR(500),     -- Increased size to 500 characters
                                  analysis_id BIGINT NOT NULL,
                                  FOREIGN KEY (analysis_id) REFERENCES medical_analysis(id) ON DELETE CASCADE
);
-- Create the sequence with increment size of 50 (matching Hibernate allocationSize)
CREATE SEQUENCE analysis_details_seq START WITH 1 INCREMENT BY 50;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE analysis_details_seq OWNED BY analysis_details.id;

-- Index for faster lookups
CREATE INDEX idx_analysis_details_analysis_id ON analysis_details (analysis_id);
