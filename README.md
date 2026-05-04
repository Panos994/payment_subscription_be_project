# Payment Subscription Management System

A Spring Boot REST API for managing users, roles, subscription plans, subscriptions, payments, and invoices.  
The project uses JWT-based authentication, validation, JPA/Hibernate, and a scheduled billing process.

## Features

- User registration and login
- JWT authentication and role-based access
- Subscription plan management
- Subscription creation and cancellation
- Payment tracking
- Invoice generation and retrieval
- Global exception handling
- Scheduled billing jobs

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT
- Maven

## Prerequisites

- Java 17+
- Maven
- PostgreSQL running locally

## Configuration

The application is configured in `src/main/resources/application.properties`.

Default settings:

- Port: `9698`
- Database: `jdbc:postgresql://localhost:5451/my_paysubscription_db`
- Username: `postgres`
- Password: `postgres`

You can also run PostgreSQL with Docker:

```bash
docker run --name my-postgres-psmgdb -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=my_paysubscription_db -p 5451:5432 -d postgres
