CREATE TABLE IF NOT EXISTS question (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    form_id BIGINT,
    question_text TEXT,
    question_type VARCHAR(50) CHECK (question_type IN ('normal_text', 'password', 'email', 'paragraph', 'multiple_choice', 'checkbox', 'dropdown', 'time', 'date')),
    is_required BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (form_id) REFERENCES form(id) ON DELETE CASCADE
);
