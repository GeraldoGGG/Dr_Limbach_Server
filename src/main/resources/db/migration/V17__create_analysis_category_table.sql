-- Create the AnalysisCategoryEntity table
CREATE TABLE IF NOT EXISTS category (
                                        id BIGSERIAL PRIMARY KEY,          -- Unique ID for the category
                                        name VARCHAR(255) NOT NULL,        -- Name of the category
                                        version BIGINT NOT NULL,              -- Version for optimistic locking (from AbstractEntity)
                                        archived BOOLEAN NOT NULL DEFAULT FALSE,
                                        removed BOOLEAN NOT NULL DEFAULT FALSE,
                                        CONSTRAINT uq_category_unique_id UNIQUE (id)  -- Ensure uniqueness for category ID
);

-- Add category_id foreign key column to the existing analysis table
ALTER TABLE analysis
    ADD COLUMN category_id BIGINT;  -- This will link analysis to a category

-- Create a foreign key constraint on category_id in analysis table
ALTER TABLE analysis
    ADD CONSTRAINT fk_analysis_category_id
        FOREIGN KEY (category_id)
            REFERENCES category(id)
            ON DELETE SET NULL;

-- Optional: If you want to make sure that an analysis can be linked to a category,
-- you can add an index on the category_id column for performance.
CREATE INDEX IF NOT EXISTS idx_analysis_category_id ON analysis (category_id);

-- If you want to ensure that the category_id is always valid, you can optionally add
-- a check constraint to validate the category_id.
