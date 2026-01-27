# Local Run (Docker)

## Prerequisites
- Docker Desktop installed and running

## Run
```bash
git clone https://github.com/andreapupilli/resource_booking_system.git
cd resource_booking_system
git checkout module2
docker compose up --build
```

## Open
- http://localhost:8080

## Stop
```bash
docker compose down
```

## Notes
- The first build may take a few minutes (image build + dependencies).
- Database persistence depends on Docker volumes configuration in `docker-compose.yml`.
