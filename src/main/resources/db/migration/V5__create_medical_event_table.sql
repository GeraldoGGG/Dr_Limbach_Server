-- Create Table: medical_event
CREATE TABLE event (
                               id BIGSERIAL PRIMARY KEY,
                               version BIGINT,
                               title VARCHAR(255) NOT NULL,
                               content TEXT,
                               event_date TIMESTAMP WITHOUT TIME ZONE,
                               event_duration BIGINT,
                               price INT,
                               archived BOOLEAN NOT NULL DEFAULT FALSE,
                               removed BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create the sequence with increment size of 50 (matching Hibernate allocationSize)
CREATE SEQUENCE event_seq START WITH 1 INCREMENT BY 1;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE event_seq OWNED BY event.id;
