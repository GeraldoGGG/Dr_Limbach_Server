-- Create Table: tag
CREATE TABLE tag (
                     id BIGSERIAL PRIMARY KEY,
                     version BIGINT,
                     name VARCHAR(255) NOT NULL UNIQUE
);

-- Optional: Index for name for faster searches
CREATE INDEX idx_tag_name ON tag (name);

-- Create many-to-many relationship tables:

-- Table: article_tag
CREATE TABLE tag_article (
                                             tag_id BIGINT NOT NULL,
                                             article_id BIGINT NOT NULL,
                                             PRIMARY KEY (tag_id, article_id),
                                             FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE,
                                             FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE
);

-- Table: tag_medical_service_entitys
CREATE TABLE tag_medical_service (
                                             tag_id BIGINT NOT NULL,
                                             medical_service_id BIGINT NOT NULL,
                                             PRIMARY KEY (tag_id, medical_service_id),
                                             FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE,
                                             FOREIGN KEY (medical_service_id) REFERENCES medical_service(id) ON DELETE CASCADE
);

-- Table: tag_medical_analysis_entitys
CREATE TABLE tag_analysis (
                                              tag_id BIGINT NOT NULL,
                                              analysis_id BIGINT NOT NULL,
                                              PRIMARY KEY (tag_id, analysis_id),
                                              FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE,
                                              FOREIGN KEY (analysis_id) REFERENCES analysis(id) ON DELETE CASCADE
);


CREATE SEQUENCE tag_seq START WITH 1 INCREMENT BY 1;
-- Set the sequence as the owner of the id column
ALTER SEQUENCE tag_seq OWNED BY tag.id;

