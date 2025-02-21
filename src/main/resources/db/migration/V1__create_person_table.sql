-- Create the sequence with increment size of 50 (matching Hibernate allocationSize)
CREATE SEQUENCE person_seq START WITH 1 INCREMENT BY 50;

-- Create the person table
CREATE TABLE person (
                        id BIGINT NOT NULL DEFAULT nextval('person_seq'),
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        email VARCHAR(255) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(50) NOT NULL,
                        version BIGINT DEFAULT 0,
                        PRIMARY KEY (id)
);

-- Set the sequence as the owner of the id column
ALTER SEQUENCE person_seq OWNED BY person.id;

-- Create an index on the email field for faster lookups
CREATE INDEX idx_person_email ON person(email);
