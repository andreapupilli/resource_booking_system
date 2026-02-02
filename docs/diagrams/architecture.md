```mermaid
flowchart LR
  U[User / Browser] -->|HTTP| C[Spring Boot Controllers]
  C --> S[Services]
  S -->|JPA / Hibernate| R[(PostgreSQL)]
  C -->|Model| T[Thymeleaf Templates]

