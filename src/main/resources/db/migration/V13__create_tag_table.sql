-- Create Table: tag
CREATE TABLE tag (
                     id BIGSERIAL PRIMARY KEY,
                     version BIGINT,
                     name VARCHAR(255) NOT NULL UNIQUE
);

-- Optional: Index for name for faster searches
CREATE INDEX idx_tag_name ON tag (name);

-- Create many-to-many relationship tables:

-- Table: medical_article_entity_tags
CREATE TABLE medical_article_entity_tags (
                                             tag_id BIGINT NOT NULL,
                                             medical_article_id BIGINT NOT NULL,
                                             PRIMARY KEY (tag_id, medical_article_id),
                                             FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE,
                                             FOREIGN KEY (medical_article_id) REFERENCES medical_article(id) ON DELETE CASCADE
);

-- Table: medical_service_entity_tags
CREATE TABLE medical_service_entity_tags (
                                             tag_id BIGINT NOT NULL,
                                             medical_service_id BIGINT NOT NULL,
                                             PRIMARY KEY (tag_id, medical_service_id),
                                             FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE,
                                             FOREIGN KEY (medical_service_id) REFERENCES medical_service(id) ON DELETE CASCADE
);

-- Table: medical_analysis_entity_tags
CREATE TABLE medical_analysis_entity_tags (
                                              tag_id BIGINT NOT NULL,
                                              medical_analysis_id BIGINT NOT NULL,
                                              PRIMARY KEY (tag_id, medical_analysis_id),
                                              FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE,
                                              FOREIGN KEY (medical_analysis_id) REFERENCES medical_analysis(id) ON DELETE CASCADE
);


CREATE SEQUENCE tag_seq START WITH 1 INCREMENT BY 50;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE tag_seq OWNED BY tag.id;

