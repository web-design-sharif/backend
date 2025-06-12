CREATE TABLE IF NOT EXISTS form_submissions (
    form_id BIGINT,
    user_id BIGINT,
    PRIMARY KEY (form_id, user_id),
    FOREIGN KEY (form_id) REFERENCES form(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
