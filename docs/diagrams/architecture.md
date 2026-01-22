```mermaid
flowchart LR
  U[User / Browser] -->|HTTP| A[Spring Boot Web App (Controllers + Services)]
  A -->|Server-side rendering| T[Thymeleaf Templates (HTML Views)]
  A -->|JPA / Hibernate| R[(PostgreSQL Database)]

  subgraph App[Application Layer]
    A
    T
  end

  subgraph Data[Data Layer]
    R
  end
