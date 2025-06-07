CREATE TABLE IF NOT EXISTS answer (
    id UUID PRIMARY KEY,
    form_response_id UUID REFERENCES form_response(id) ON DELETE CASCADE,
    question_id UUID REFERENCES question(id) ON DELETE CASCADE,
    answer_text TEXT,
    created_at TIMESTAMP DEFAULT now()
);