# Deploy on Render

## Services
- Web Service: Spring Boot application (Docker)
- Database: Render managed PostgreSQL

## Web Service URL
- https://resource-booking-system-jjvs.onrender.com

## Environment Variables
Configured in Render dashboard:

- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD
- SPRING_JPA_HIBERNATE_DDL_AUTO=update
- PORT=8080

## Deploy workflow
1. Push code to GitHub (branch `module2`)
2. GitHub Actions runs CI pipeline
3. Render builds the Docker image
4. Application is deployed and connected to PostgreSQL
