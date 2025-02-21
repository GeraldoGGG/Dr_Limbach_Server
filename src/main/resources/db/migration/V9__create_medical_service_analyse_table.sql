-- Create Join Table: medical_service_analyse (MedicalService ↔ MedicalAnalysis)
CREATE TABLE medical_service_analyse (
                                         medical_service_id BIGINT NOT NULL,
                                         medical_analyse_id BIGINT NOT NULL,
                                         PRIMARY KEY (medical_service_id, medical_analyse_id),
                                         FOREIGN KEY (medical_service_id) REFERENCES medical_service (id) ON DELETE CASCADE,
                                         FOREIGN KEY (medical_analyse_id) REFERENCES medical_analysis (id) ON DELETE CASCADE
);

-- Index for faster lookups on the join table
CREATE INDEX idx_medical_service_id_analyse ON medical_service_analyse (medical_service_id);
CREATE INDEX idx_medical_analyse_id ON medical_service_analyse (medical_analyse_id);
