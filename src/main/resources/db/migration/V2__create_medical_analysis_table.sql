-- Create Table: medical_analysis
CREATE TABLE analysis (
                                  id BIGSERIAL PRIMARY KEY,
                                  version BIGINT,
                                  medical_name VARCHAR(255) NOT NULL,
                                  synonym VARCHAR(255),
                                  price INT,
                                  archived BOOLEAN NOT NULL DEFAULT FALSE,
                                  removed BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create the sequence with increment size of 50 (matching Hibernate allocationSize)
CREATE SEQUENCE analysis_seq START WITH 1 INCREMENT BY 1;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE analysis_seq OWNED BY analysis.id;
