-- V3__add_creation_date_to_article.sql

-- Add new column to the article table
ALTER TABLE article
    ADD COLUMN creation_date TIMESTAMP;

ALTER TABLE article
    ADD COLUMN summary VARCHAR(510);