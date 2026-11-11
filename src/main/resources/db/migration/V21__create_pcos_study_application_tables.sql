-- Create Table: pcos_study_application
CREATE TABLE pcos_study_application (
                                        id BIGSERIAL PRIMARY KEY,
                                        version BIGINT,
                                        first_name VARCHAR(255) NOT NULL,
                                        last_name VARCHAR(255) NOT NULL,
                                        age INTEGER NOT NULL,
                                        email VARCHAR(255) NOT NULL,
                                        study_group VARCHAR(255) NOT NULL,
                                        created_at TIMESTAMP NOT NULL
);

-- One application per email (case-insensitive)
CREATE UNIQUE INDEX uq_pcos_study_application_email ON pcos_study_application (lower(email));
CREATE INDEX idx_pcos_study_application_study_group ON pcos_study_application (study_group);

-- Create Join Table: study_application_condition (Q2 - multi select)
CREATE TABLE study_application_condition (
                                             application_id BIGINT NOT NULL,
                                             health_condition VARCHAR(255) NOT NULL,
                                             PRIMARY KEY (application_id, health_condition),
                                             FOREIGN KEY (application_id) REFERENCES pcos_study_application (id) ON DELETE CASCADE
);

-- Create Join Table: study_application_medication (Q3 - multi select)
CREATE TABLE study_application_medication (
                                              application_id BIGINT NOT NULL,
                                              medication VARCHAR(255) NOT NULL,
                                              PRIMARY KEY (application_id, medication),
                                              FOREIGN KEY (application_id) REFERENCES pcos_study_application (id) ON DELETE CASCADE
);

-- Indexes for filtering by option
CREATE INDEX idx_study_application_condition_value ON study_application_condition (health_condition);
CREATE INDEX idx_study_application_medication_value ON study_application_medication (medication);
