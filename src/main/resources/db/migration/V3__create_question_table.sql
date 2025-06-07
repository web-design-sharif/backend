CREATE TABLE IF NOT EXISTS question (
    id UUID PRIMARY KEY,
    form_id UUID REFERENCES form(id) ON DELETE CASCADE,
    question_text TEXT,
    question_type VARCHAR(50) CHECK (question_type IN ('normal_text', 'password', 'email', 'paragraph' , 'multiple_choice' , 'checkbox' , 'dropdown' , 'time' , 'date')),
    is_required BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT now(),
    updated_at TIMESTAMP DEFAULT now()
);