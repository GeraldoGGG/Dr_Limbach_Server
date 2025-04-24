-- Enable the extension for full-text search (if not already enabled)
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-- Add the search_vector columns to relevant tables

-- For Event Table
ALTER TABLE event ADD COLUMN search_vector tsvector;

-- For Analysis Table
ALTER TABLE analysis ADD COLUMN search_vector tsvector;

-- For Category Table
ALTER TABLE category ADD COLUMN search_vector tsvector;

-- For Medical Service Table
ALTER TABLE medical_service ADD COLUMN search_vector tsvector;

-- For Article Table
ALTER TABLE article ADD COLUMN search_vector tsvector;

-- For Package Table
ALTER TABLE package ADD COLUMN search_vector tsvector;

-- Add a search_vector column for PackageAnalysis Table (if not already added)
ALTER TABLE package_analysis ADD COLUMN search_vector tsvector;

-- Add search_vector for AnalysisDetail Table (if applicable)
ALTER TABLE analysis_detail ADD COLUMN search_vector tsvector;

-- Update the search_vector columns with full-text search data

-- For Event Table
UPDATE event SET search_vector = to_tsvector('simple', title);

-- For Analysis Table
UPDATE analysis SET search_vector = to_tsvector('simple', medical_name);

-- For Category Table
UPDATE category SET search_vector = to_tsvector('simple', name);

-- For Medical Service Table
UPDATE medical_service SET search_vector = to_tsvector('simple', title);

-- For Article Table
UPDATE article SET search_vector = to_tsvector('simple', title);

-- For Package Table
UPDATE package SET search_vector = to_tsvector('simple', name);

-- For PackageAnalysis Table
UPDATE package_analysis SET search_vector = to_tsvector('simple', analysis_id::text);

-- For AnalysisDetail Table
UPDATE analysis_detail SET search_vector = to_tsvector('simple', key_value || ' ' || string_value);

-- Create the indexes for full-text search to speed up queries

-- For Event Table
CREATE INDEX idx_event_search_vector ON event USING gin(search_vector);

-- For Analysis Table
CREATE INDEX idx_analysis_search_vector ON analysis USING gin(search_vector);

-- For Category Table
CREATE INDEX idx_category_search_vector ON category USING gin(search_vector);

-- For Medical Service Table
CREATE INDEX idx_medical_service_search_vector ON medical_service USING gin(search_vector);

-- For Article Table
CREATE INDEX idx_article_search_vector ON article USING gin(search_vector);

-- For Package Table
CREATE INDEX idx_package_search_vector ON package USING gin(search_vector);

-- For PackageAnalysis Table
CREATE INDEX idx_package_analysis_search_vector ON package_analysis USING gin(search_vector);

-- For AnalysisDetail Table
CREATE INDEX idx_analysis_detail_search_vector ON analysis_detail USING gin(search_vector);

-- Create triggers to keep the search_vector column updated

-- Trigger for Event Table
CREATE OR REPLACE FUNCTION update_event_search_vector() RETURNS trigger AS $$
BEGIN
    NEW.search_vector := to_tsvector('simple', NEW.title);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER event_search_vector_update
    BEFORE INSERT OR UPDATE ON event
    FOR EACH ROW EXECUTE FUNCTION update_event_search_vector();

-- Trigger for Analysis Table
CREATE OR REPLACE FUNCTION update_analysis_search_vector() RETURNS trigger AS $$
BEGIN
    NEW.search_vector := to_tsvector('simple', NEW.medical_name);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER analysis_search_vector_update
    BEFORE INSERT OR UPDATE ON analysis
    FOR EACH ROW EXECUTE FUNCTION update_analysis_search_vector();

-- Trigger for Category Table
CREATE OR REPLACE FUNCTION update_category_search_vector() RETURNS trigger AS $$
BEGIN
    NEW.search_vector := to_tsvector('simple', NEW.name);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER category_search_vector_update
    BEFORE INSERT OR UPDATE ON category
    FOR EACH ROW EXECUTE FUNCTION update_category_search_vector();

-- Trigger for Medical Service Table
CREATE OR REPLACE FUNCTION update_medical_service_search_vector() RETURNS trigger AS $$
BEGIN
    NEW.search_vector := to_tsvector('simple', NEW.title);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER medical_service_search_vector_update
    BEFORE INSERT OR UPDATE ON medical_service
    FOR EACH ROW EXECUTE FUNCTION update_medical_service_search_vector();

-- Trigger for Article Table
CREATE OR REPLACE FUNCTION update_article_search_vector() RETURNS trigger AS $$
BEGIN
    NEW.search_vector := to_tsvector('simple', NEW.title);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER article_search_vector_update
    BEFORE INSERT OR UPDATE ON article
    FOR EACH ROW EXECUTE FUNCTION update_article_search_vector();

-- Trigger for Package Table
CREATE OR REPLACE FUNCTION update_package_search_vector() RETURNS trigger AS $$
BEGIN
    NEW.search_vector := to_tsvector('simple', NEW.name);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER package_search_vector_update
    BEFORE INSERT OR UPDATE ON package
    FOR EACH ROW EXECUTE FUNCTION update_package_search_vector();

-- Trigger for PackageAnalysis Table
CREATE OR REPLACE FUNCTION update_package_analysis_search_vector() RETURNS trigger AS $$
BEGIN
    NEW.search_vector := to_tsvector('simple', NEW.analysis_id::text);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER package_analysis_search_vector_update
    BEFORE INSERT OR UPDATE ON package_analysis
    FOR EACH ROW EXECUTE FUNCTION update_package_analysis_search_vector();

-- Trigger for AnalysisDetail Table
CREATE OR REPLACE FUNCTION update_analysis_detail_search_vector() RETURNS trigger AS $$
BEGIN
    NEW.search_vector := to_tsvector('simple', NEW.key_value || ' ' || NEW.string_value);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER analysis_detail_search_vector_update
    BEFORE INSERT OR UPDATE ON analysis_detail
    FOR EACH ROW EXECUTE FUNCTION update_analysis_detail_search_vector();

