CREATE TABLE IF NOT EXISTS form_response (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    form_id BIGINT REFERENCES form(id) ON DELETE CASCADE,
    responder_id BIGINT REFERENCES users(id), -- nullable if anonymous
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
