```mermaid
flowchart LR
  U[User / Browser] -->|HTTP| A[Spring Boot Web App<br/>(Controllers + Services)]
  A -->|Server-side rendering| T[Thymeleaf Templates<br/>(HTML Views)]
  A -->|JPA / Hibernate| R[(PostgreSQL Database)]

  subgraph App[Application Layer]
    A
    T
  end

  subgraph Data[Data Layer]
    R
  end
