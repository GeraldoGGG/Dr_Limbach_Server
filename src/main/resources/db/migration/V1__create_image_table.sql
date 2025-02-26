CREATE TABLE image (
                       id BIGSERIAL PRIMARY KEY,
                       version BIGINT,
                       entity_reference_id BIGINT,
                       entity_type VARCHAR(255) NOT NULL,
                       file_name VARCHAR(255),
                       height INT,
                       width INT,
                       image_data BYTEA,
                       file_key VARCHAR(255),
                       file_version_id VARCHAR(255)
);
-- Create the sequence with increment size of 50 (matching Hibernate allocationSize)
CREATE SEQUENCE image_seq START WITH 1 INCREMENT BY 1;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE image_seq OWNED BY image.id;
