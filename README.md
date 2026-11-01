# Library Management System (LMS)

[![Java](https://img.shields.io/badge/Java-17%2B-orange.svg)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen.svg)](#)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](#)

> Production-ready RESTful backend service designed for managing books, members, reservations, and fine calculations with role-based access control and concurrency-safe transactions.

---

## Table of Contents
- [Architecture & Tech Stack](#-architecture--tech-stack)
- [Key Features](#-key-features)
- [Database Schema (ERD)](#-database-schema-erd)
- [API Documentation](#-api-documentation)
- [Technical Highlights & Engineering Decisions](#-technical-highlights--engineering-decisions)
- [Local Setup & Installation](#-local-setup--installation)
- [Testing](#-testing)
- [Future Roadmap](#-future-roadmap)

---

##  Architecture & Tech Stack

- **Backend:** Java 17+, Spring Boot (Spring Security, Spring Data JPA)
- **Database:** PostgreSQL 
- **Caching & Rate Limiting:** Redis
- **Authentication:** JWT Authentication
- **Build Tool:** Maven
- **Containerization:** Docker & Docker Compose

### System Architecture Flow
[Client / Swagger UI]
│
HTTP Request (JWT Bearer Token)
▼
[Spring Security Filter Chain]
▼
[Controller Layer (REST Endpoints & DTO Validation)]
▼
[Service Layer (Business Logic & Transactions)]
▼
[Repository Layer (Spring Data JPA)]
▼
[PostgreSQL Database]

---

## Key Features

1. **Role-Based Access Control (RBAC):**
    - `ADMIN`: Manage book inventory, issue cards, track fines, view analytics.
    - `MEMBER`: Search books, reserve titles, view borrow history, track dues.
2. **Book Lifecycle & Inventory Management:**
    - Multi-copy tracking (unique barcode/ISBN tracking per copy).
    - Real-time availability status (`AVAILABLE`, `ISSUED`, `RESERVED`, `LOST`).
3. **Borrow & Return Engine:**
    - Configurable borrow limits (e.g., max 3 books per member for 14 days).
    - Automated fine calculation engine for overdue days.
4. **Reservation Queue:**
    - FIFO queue system for popular books currently out of stock.

---

## Database Schema (ERD)

Key Entities & Relationships:
- `users` (id, email, password, role, status, address, contact no)
- `books` (id, bookTitle, bookAuthor, category, length, price, publish date, bookType)



[//]: # (```mermaid)

[//]: # (erDiagram)

[//]: # (    USER ||--o{ BORROW_RECORD : places)

[//]: # (    BOOK ||--|{ BOOK_COPY : contains)

[//]: # (    BOOK_COPY ||--o{ BORROW_RECORD : logged_in)