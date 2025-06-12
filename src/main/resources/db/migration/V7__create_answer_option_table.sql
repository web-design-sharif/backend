CREATE TABLE IF NOT EXISTS answer_option (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    answer_id BIGINT,
    option_id BIGINT,
    FOREIGN KEY (answer_id) REFERENCES answer(id) ON DELETE CASCADE,
    FOREIGN KEY (option_id) REFERENCES question_option(id)
);
