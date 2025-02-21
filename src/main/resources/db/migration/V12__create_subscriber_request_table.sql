-- Create Table: subscriber_request
CREATE TABLE subscriber_request (
                                    id BIGSERIAL PRIMARY KEY,
                                    version BIGINT,
                                    subscriber_email VARCHAR(255) NOT NULL UNIQUE
);

-- Optional: Index for subscriber_email (for faster lookup)
CREATE INDEX idx_subscriber_request_email ON subscriber_request (subscriber_email);

CREATE SEQUENCE subscriber_request_seq START WITH 1 INCREMENT BY 50;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE subscriber_request_seq OWNED BY subscriber_request.id;

