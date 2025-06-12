CREATE TABLE IF NOT EXISTS question_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT,
    option_text VARCHAR(255),
    FOREIGN KEY (question_id) REFERENCES question(id) ON DELETE CASCADE
);
