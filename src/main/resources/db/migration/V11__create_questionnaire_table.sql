-- Create Table: questionnaire
CREATE TABLE questionnaire (
                               id BIGSERIAL PRIMARY KEY,
                               version BIGINT,
                               question VARCHAR(255) NOT NULL,
                               answer TEXT,
                               business_module VARCHAR(255) NOT NULL
);

-- Optional: Index for business_module (for optimized queries)
CREATE INDEX idx_questionnaire_business_module ON questionnaire (business_module);

CREATE SEQUENCE questionnaire_seq START WITH 1 INCREMENT BY 1;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE questionnaire_seq OWNED BY questionnaire.id;
