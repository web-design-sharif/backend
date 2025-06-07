CREATE TABLE IF NOT EXISTS answer_option (
    id UUID PRIMARY KEY,
    answer_id UUID REFERENCES answer(id) ON DELETE CASCADE,
    option_id UUID REFERENCES option(id)
);
