# CI/CD Pipeline

## Platform
- GitHub Actions

## Workflow
- Defined in `.github/workflows/ci.yml`
- Triggered on push (and pull request) to branch `module2`

## Build command
- `./mvnw -B verify`

## Steps
- Checkout repository
- Set up Java 21
- Run Maven build and tests

## Purpose
Ensure the application builds correctly and tests pass automatically before deployment.
