CREATE SEQUENCE email_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE email (
                        id BIGINT NOT NULL DEFAULT nextval('email_seq'),
                        version BIGINT,
                        email_address VARCHAR(255) NOT NULL,
                        PRIMARY KEY (id)
);

-- Set the sequence as the owner of the id column
ALTER SEQUENCE email_seq OWNED BY email.id;