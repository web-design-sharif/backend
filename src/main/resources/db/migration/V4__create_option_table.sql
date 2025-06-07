CREATE TABLE IF NOT EXISTS option (
    id UUID PRIMARY KEY,
    question_id UUID REFERENCES question(id) ON DELETE CASCADE,
    option_text VARCHAR(255),
);