CREATE TABLE IF NOT EXISTS form_response (
    id UUID PRIMARY KEY,
    form_id UUID REFERENCES form(id) ON DELETE CASCADE,
    responder_id UUID REFERENCES users(id), -- nullable if anonymous
    submitted_at TIMESTAMP DEFAULT now()
);