-- Create Table: medical_analysis
CREATE TABLE medical_analysis (
                                  id BIGSERIAL PRIMARY KEY,
                                  version BIGINT,
                                  medical_name VARCHAR(255) NOT NULL,
                                  synonym VARCHAR(255),
                                  price INT,
                                  archived BOOLEAN NOT NULL DEFAULT FALSE,
                                  removed BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create the sequence with increment size of 50 (matching Hibernate allocationSize)
CREATE SEQUENCE medical_analysis_seq START WITH 1 INCREMENT BY 50;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE medical_analysis_seq OWNED BY medical_analysis.id;
