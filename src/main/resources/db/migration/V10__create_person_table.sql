-- Create Table: person
CREATE TABLE person (
                        id BIGSERIAL PRIMARY KEY,
                        version BIGINT,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(255) NOT NULL
);

CREATE SEQUENCE person_seq START WITH 1 INCREMENT BY 1;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE person_seq OWNED BY person.id;


-- Optional: Index for email (for faster lookup)
CREATE INDEX idx_person_email ON person (email);
