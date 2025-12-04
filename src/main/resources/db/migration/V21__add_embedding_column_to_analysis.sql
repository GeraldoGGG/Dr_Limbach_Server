-- 1️⃣ Ensure pgvector extension exists
CREATE EXTENSION IF NOT EXISTS vector;

-- 2️⃣ Add the AI embeddings column for OpenAI semantic search
ALTER TABLE analysis
    ADD COLUMN ai_analysis_embedding vector(1536);

-- 3️⃣ Optional: Create an index for fast cosine similarity search
CREATE INDEX idx_ai_analysis_embedding
    ON analysis
        USING ivfflat (ai_analysis_embedding vector_cosine_ops)
    WITH (lists = 100);
