CREATE TABLE IF NOT EXISTS form_response (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    form_id BIGINT,
    responder_id BIGINT, -- nullable if anonymous
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (form_id) REFERENCES form(id) ON DELETE CASCADE,
    FOREIGN KEY (responder_id) REFERENCES users(id)
);
