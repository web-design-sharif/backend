# Data Models Documentation

## Users Table

Stores user information.

| Column   | Type         | Constraints                  | Description            |
| -------- | ------------ | ---------------------------- | ---------------------- |
| id       | BIGINT       | PRIMARY KEY, AUTO\_INCREMENT | Unique user ID         |
| email    | VARCHAR(255) | NOT NULL, UNIQUE             | User email address     |
| password | VARCHAR(255) | NOT NULL                     | User password (hashed) |

---

## Form Table

Stores forms created by users.

| Column        | Type         | Constraints                                             | Description                    |
| ------------- | ------------ | ------------------------------------------------------- | ------------------------------ |
| id            | BIGINT       | PRIMARY KEY, AUTO\_INCREMENT                            | Unique form ID                 |
| owner\_id     | BIGINT       | FOREIGN KEY REFERENCES users(id)                        | ID of the form owner           |
| title         | VARCHAR(255) |                                                         | Title of the form              |
| description   | TEXT         |                                                         | Description of the form        |
| is\_published | BOOLEAN      | DEFAULT FALSE                                           | Indicates if form is published |
| created\_at   | TIMESTAMP    | DEFAULT CURRENT\_TIMESTAMP                              | Creation timestamp             |
| updated\_at   | TIMESTAMP    | DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP | Last update timestamp          |

---

## Question Table

Stores questions associated with forms.

| Column         | Type        | Constraints                                             | Description                                    |
| -------------- | ----------- | ------------------------------------------------------- | ---------------------------------------------- |
| id             | BIGINT      | PRIMARY KEY, AUTO\_INCREMENT                            | Unique question ID                             |
| form\_id       | BIGINT      | FOREIGN KEY REFERENCES form(id)                         | Associated form ID                             |
| title          | TEXT        |                                                         | Question text                                  |
| question\_type | VARCHAR(50) |                                                         | Type of question (text, multiple choice, etc.) |
| is\_required   | BOOLEAN     | DEFAULT TRUE                                            | Indicates if question is required              |
| created\_at    | TIMESTAMP   | DEFAULT CURRENT\_TIMESTAMP                              | Creation timestamp                             |
| updated\_at    | TIMESTAMP   | DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP | Last update timestamp                          |

---

## Question Option Table

Stores options for multiple-choice questions.

| Column       | Type         | Constraints                         | Description            |
| ------------ | ------------ | ----------------------------------- | ---------------------- |
| id           | BIGINT       | PRIMARY KEY, AUTO\_INCREMENT        | Unique option ID       |
| question\_id | BIGINT       | FOREIGN KEY REFERENCES question(id) | Associated question ID |
| option\_text | VARCHAR(255) |                                     | Option text            |

---

## Form Response Table

Stores user submissions of forms.

| Column        | Type      | Constraints                      | Description                   |
| ------------- | --------- | -------------------------------- | ----------------------------- |
| id            | BIGINT    | PRIMARY KEY, AUTO\_INCREMENT     | Unique form response ID       |
| form\_id      | BIGINT    | FOREIGN KEY REFERENCES form(id)  | Form being submitted          |
| responder\_id | BIGINT    | FOREIGN KEY REFERENCES users(id) | User who submitted (nullable) |
| submitted\_at | TIMESTAMP | DEFAULT CURRENT\_TIMESTAMP       | Submission timestamp          |

---

## Answer Table

Stores answers submitted for each question.

| Column             | Type      | Constraints                               | Description                 |
| ------------------ | --------- | ----------------------------------------- | --------------------------- |
| id                 | BIGINT    | PRIMARY KEY, AUTO\_INCREMENT              | Unique answer ID            |
| form\_response\_id | BIGINT    | FOREIGN KEY REFERENCES form\_response(id) | Associated form response ID |
| question\_id       | BIGINT    | FOREIGN KEY REFERENCES question(id)       | Question being answered     |
| answer\_text       | TEXT      |                                           | Answer content              |
| created\_at        | TIMESTAMP | DEFAULT CURRENT\_TIMESTAMP                | Creation timestamp          |

---

## Answer Option Table

Stores selected options for multiple-choice answers.

| Column     | Type   | Constraints                                 | Description          |
| ---------- | ------ | ------------------------------------------- | -------------------- |
| id         | BIGINT | PRIMARY KEY, AUTO\_INCREMENT                | Unique ID            |
| answer\_id | BIGINT | FOREIGN KEY REFERENCES answer(id)           | Associated answer ID |
| option\_id | BIGINT | FOREIGN KEY REFERENCES question\_option(id) | Selected option ID   |

---

## Form Submissions Table

Tracks which users have submitted which forms.

| Column                           | Type   | Constraints                                                       | Description             |
| -------------------------------- | ------ | ----------------------------------------------------------------- | ----------------------- |
| form\_id                         | BIGINT | FOREIGN KEY REFERENCES form(id)                                   | Submitted form ID       |
| user\_id                         | BIGINT | FOREIGN KEY REFERENCES users(id)                                  | User who submitted form |
| PRIMARY KEY (form\_id, user\_id) |        | Composite primary key ensures unique submission per user per form |                         |

