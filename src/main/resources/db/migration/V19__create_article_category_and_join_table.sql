-- Create Table: article_category
CREATE TABLE article_category (
                                  id BIGSERIAL PRIMARY KEY,
                                  version BIGINT,
                                  name VARCHAR(255) NOT NULL
);

-- Create Join Table: article_article_category (Article ↔ ArticleCategory)
CREATE TABLE article_article_category (
                                          article_id BIGINT NOT NULL,
                                          article_category_id BIGINT NOT NULL,
                                          PRIMARY KEY (article_id, article_category_id),
                                          FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE,
                                          FOREIGN KEY (article_category_id) REFERENCES article_category (id) ON DELETE CASCADE
);



-- Index for faster lookups on the join table
CREATE INDEX idx_article_category_id ON article_article_category (article_category_id);
