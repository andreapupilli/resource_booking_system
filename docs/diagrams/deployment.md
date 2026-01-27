flowchart LR
DEV[Developer] -->|push| GH[GitHub Repository]
GH -->|CI build and test| CI[GitHub Actions]
GH -->|deploy from branch module2| RWS[Render Web Service]
RWS -->|connect| RDB[(Render PostgreSQL)]
USER[User Browser] -->|https| RWS
