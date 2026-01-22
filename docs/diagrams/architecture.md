```mermaid
flowchart LR
  U[Browser] -->|HTTP| A[Spring Boot]
  A -->|HTML views| T[Thymeleaf]
  A -->|JPA| R[(PostgreSQL)]

