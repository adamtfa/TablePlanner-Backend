<p align="center">
  <img src="tableplanner_logo.png" alt="TablePlanner logo" width="90" />
</p>

<h1 align="center">TablePlanner — Backend</h1>

The backend for **TablePlanner**, a web application that lets restaurants manage
their tables, guests, and reservations. This repository contains the REST API,
the database layer, and the authentication logic.

The matching frontend lives in [TablePlanner-Frontend](https://github.com/adamtfa/TablePlanner-Frontend).

## What the app can do

Each restaurant has its own account and manages its own isolated set of data:

- **Authentication** — register a restaurant account and log in. All further
  requests are authorized via a JWT.
- **Multi-tenancy** — every restaurant only ever sees and edits its own tables,
  customers, and reservations. All data is fully isolated per account.
- **Tables** — full CRUD (create, read, update, delete) with a live updated
  availability status.
- **Customers** — full CRUD for the restaurant's guest database.
- **Reservations** — full CRUD, linking a customer and a table at a given date
  and time.
- **Referential safety** — deleting a table or customer that is still referenced
  by a reservation is rejected with `409 Conflict` instead of corrupting data.

## Tech stack & methods

- **Java 25** with **Spring Boot**
- **Spring Web** — REST controllers returning `ResponseEntity<T>` with correct
  HTTP status codes (`200` GET/PUT, `201` POST, `204` DELETE)
- **Spring Data JPA / Hibernate** — object-relational mapping of the entities
- **Spring Security** — `SecurityFilterChain` bean, BCrypt password hashing,
  and a custom `JwtFilter` (`OncePerRequestFilter`) that reads the current
  restaurant from the token
- **PostgreSQL** in production, **H2** (in-memory) for tests
- **Lombok** (`@Getter`, `@Setter`, `@Slf4j`, …)
- **SLF4J logging** on all create/update/delete operations
- Centralized error handling via `@RestControllerAdvice` (`GlobalExceptionHandler`):
  `404` for not-found, `409` for integrity violations, `500` as fallback
- **Docker** for deployment on **Render**

Table availability is **not stored** but computed on the fly: a table counts as
unavailable while the current time (in `Europe/Berlin`) falls within a
reservation's 120-minute window.

## Developer Team
- Nando Patton
- Adam Tuffaha
