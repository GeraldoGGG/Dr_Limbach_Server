-- Create Table: medical_article
CREATE TABLE article (
                                 id BIGSERIAL PRIMARY KEY,
                                 version BIGINT,
                                 title VARCHAR(255) NOT NULL,
                                 author VARCHAR(255),
                                 content TEXT,
                                 show_home_page BOOLEAN NOT NULL DEFAULT FALSE,
                                 archived BOOLEAN NOT NULL DEFAULT FALSE,
                                 removed BOOLEAN NOT NULL DEFAULT FALSE
);

-- Create the sequence with increment size of 50 (matching Hibernate allocationSize)
CREATE SEQUENCE article_seq START WITH 1 INCREMENT BY 1;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE article_seq OWNED BY article.id;

