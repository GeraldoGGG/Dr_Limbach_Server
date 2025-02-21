-- Create Table: medical_service (This table should be created before the join table)
CREATE TABLE medical_service (
                                 id BIGSERIAL PRIMARY KEY,
                                 version BIGINT,
                                 show_in_home_page BOOLEAN,
                                 title VARCHAR(255),
                                 archived BOOLEAN NOT NULL DEFAULT FALSE,
                                 removed BOOLEAN NOT NULL DEFAULT FALSE,
                                 content TEXT
);

-- Create the sequence with increment size of 50 (matching Hibernate allocationSize)
CREATE SEQUENCE medical_service_seq START WITH 1 INCREMENT BY 50;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE medical_service_seq OWNED BY medical_service.id;
