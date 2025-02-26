-- Create Join Table: medical_service_analysis (MedicalService ↔ MedicalAnalysis)
CREATE TABLE medical_service_analysis (
                                         medical_service_id BIGINT NOT NULL,
                                         analysis_id BIGINT NOT NULL,
                                         PRIMARY KEY (medical_service_id, analysis_id),
                                         FOREIGN KEY (medical_service_id) REFERENCES medical_service (id) ON DELETE CASCADE,
                                         FOREIGN KEY (analysis_id) REFERENCES analysis (id) ON DELETE CASCADE
);

-- Index for faster lookups on the join table
CREATE INDEX idx_medical_service_id_analysis ON medical_service_analysis (medical_service_id);
CREATE INDEX idx_analysis_id ON medical_service_analysis (analysis_id);
