CREATE TABLE medical_service_article (
                                         medical_service_id BIGINT NOT NULL,
                                         article_id BIGINT NOT NULL,
                                         PRIMARY KEY (medical_service_id, article_id),
                                         FOREIGN KEY (medical_service_id) REFERENCES medical_service (id) ON DELETE CASCADE,
                                         FOREIGN KEY (article_id) REFERENCES article (id) ON DELETE CASCADE
);

-- Index for faster lookups on the join table
CREATE INDEX idx_medical_service_id_article ON medical_service_article (medical_service_id);
CREATE INDEX idx_article_id ON medical_service_article (article_id);
