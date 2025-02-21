-- Create Table: medical_article
CREATE TABLE medical_article (
                                 id BIGSERIAL PRIMARY KEY,
                                 version BIGINT,
                                 title VARCHAR(255) NOT NULL,
                                 author VARCHAR(255),
                                 content TEXT,
                                 archived BOOLEAN NOT NULL DEFAULT FALSE,
                                 removed BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create the sequence with increment size of 50 (matching Hibernate allocationSize)
CREATE SEQUENCE medical_article_seq START WITH 1 INCREMENT BY 50;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE medical_article_seq OWNED BY medical_article.id;

