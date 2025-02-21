-- Create Table: medical_analysis_package
CREATE TABLE package (
                                          id BIGSERIAL PRIMARY KEY,
                                          version BIGINT,
                                          price INT,
                                          discount_percentage INT,
                                          promoted BOOLEAN NOT NULL DEFAULT FALSE,
                                          archived BOOLEAN NOT NULL DEFAULT FALSE,
                                          removed BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create Join Table: medical_package_analysis (AnalysisPackage ↔ MedicalAnalysis)
CREATE TABLE package_analysis (
                                          package_id BIGINT NOT NULL,
                                          analysis_id BIGINT NOT NULL,
                                          PRIMARY KEY (package_id, analysis_id),
                                          FOREIGN KEY (package_id) REFERENCES package (id) ON DELETE CASCADE,
                                          FOREIGN KEY (analysis_id) REFERENCES medical_analysis (id) ON DELETE CASCADE
);


-- Create the sequence with increment size of 50 (matching Hibernate allocationSize)
CREATE SEQUENCE package_seq START WITH 1 INCREMENT BY 50;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE package_seq OWNED BY package.id;


-- Index for faster lookups on the join table
CREATE INDEX idx_package_id ON package_analysis (package_id);
