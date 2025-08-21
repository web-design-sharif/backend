
## Authentication API

### POST /sign-up

Register a new user.

**Request Body:**

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (201 Created):**

```json
{
  "id": 1,
  "email": "user@example.com",
  "password": "password123"
}
```

---

### POST /sign-in

Authenticate an existing user.

**Request Body:**

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response (200 OK):**

```json
"jwt_token_here"
```

**Error Response (401 Unauthorized):**

```json
{
  "error": "Invalid user credentials!"
}
```

---

## Forms API

**All endpoints require authentication (JWT).**

### POST /forms

Create a new form.

**Request Body:**

```json
{
  "formDTO": {
    "title": "Sample Form",
    "question": [
      {
        "id": 1,
        "text": "Question 1",
        "type": "text"
      }
    ]
  }
}
```

**Response (201 Created)**

---

### POST /forms/{formId}/publish

Publish a form by ID.

**Response (200 OK)**

---

### DELETE /forms/{formId}

Delete a form by ID.

**Response (204 No Content)**

---

### GET /forms/{formId}

Get a form by ID.

**Response (200 OK):**

```json
{
  "id": 1,
  "ownerId": 2,
  "title": "Sample Form",
  "isPublished": true,
  "submitters": [],
  "question": [],
  "createdAt": "2025-08-21T12:00:00",
  "updatedAt": "2025-08-21T12:00:00"
}
```

---

### GET /forms

Get all forms created by the authenticated user.

**Response (200 OK):**

```json
[
  {
    "id": 1,
    "ownerId": 2,
    "title": "Sample Form",
    "isPublished": false,
    "submitters": [],
    "question": [],
    "createdAt": "2025-08-21T12:00:00",
    "updatedAt": "2025-08-21T12:00:00"
  }
]
```

---

### GET /forms/pending

Get all pending forms for the authenticated user.

**Response (200 OK):**

```json
[
  {
    "id": 2,
    "ownerId": 2,
    "title": "Pending Form",
    "isPublished": false,
    "submitters": [],
    "question": [],
    "createdAt": "2025-08-21T12:00:00",
    "updatedAt": "2025-08-21T12:00:00"
  }
]
```

---

## Form Responses API

### POST /response/submit

Submit responses for a form.

**Request Body:**

```json
{
  "userId": 1,
  "formId": 2,
  "formResponses": [
    {
      "formId": 2,
      "responderId": 1,
      "answers": [
        {
          "questionId": 1,
          "answerText": "My answer"
        }
      ],
      "submittedAt": "2025-08-21T12:00:00"
    }
  ]
}
```

**Response (200 OK)**

---

### GET /response/{formId}/responses

Get all responses for a specific form.

**Response (200 OK):**

```json
[
  {
    "id": 1,
    "formId": 2,
    "responderId": 1,
    "answers": [
      {
        "questionId": 1,
        "answerText": "My answer"
      }
    ],
    "submittedAt": "2025-08-21T12:00:00"
  }
]
```

---

## Data Models

**UserDTO**

```json
{
  "id": 1,
  "email": "user@example.com",
  "password": "password123"
}
```

**AuthRequestDTO**

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**FormDTO**

```json
{
  "id": 1,
  "ownerId": 2,
  "title": "Sample Form",
  "isPublished": true,
  "submitters": [],
  "question": [],
  "createdAt": "2025-08-21T12:00:00",
  "updatedAt": "2025-08-21T12:00:00"
}
```

**FormResponseDTO**

```json
{
  "id": 1,
  "formId": 2,
  "responderId": 1,
  "answers": [],
  "submittedAt": "2025-08-21T12:00:00"
}
```

