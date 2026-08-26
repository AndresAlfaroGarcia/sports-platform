# Sports Platform – Developer Setup

This guide explains how to set up the development environment and run the project locally.

## Prerequisites

Install the following software before starting:

* Java 21
* IntelliJ IDEA
* Docker Desktop
* Git
* DBeaver (optional, for database inspection)

The project uses the Maven Wrapper, so a local Maven installation is not required.

## Clone the Repository

```bash
git clone git@github.com:AndresAlfaroGarcia/sports-platform.git
cd sports-platform
```

## Commands more used
Action		Comando

Start PostgreSQL -> docker compose up -d
	
Start the service -> ./mvnw spring-boot:run
	
Run all tests -> ./mvnw test

Health check -> http://localhost:8081/actuator/health


## Configure Git SSH (macOS)

### Check your SSH keys

```bash
ls ~/.ssh
```

You should see something similar to:

```text
id_ed25519
id_ed25519.pub
```

### Load the SSH key into the Keychain

```bash
ssh-add --apple-use-keychain ~/.ssh/id_ed25519
```

### Verify GitHub authentication

```bash
ssh -T git@github.com
```

Expected output:

```text
Hi YOUR_USERNAME! You've successfully authenticated.
```

### Recommended SSH configuration

Create or edit:

```text
~/.ssh/config
```

```text
Host github.com
  HostName github.com
  User git
  IdentityFile ~/.ssh/id_ed25519
  AddKeysToAgent yes
  UseKeychain yes
```

This prevents authentication issues after restarting your Mac.

## Start PostgreSQL

Navigate to the directory containing `docker-compose.yml` and run:

```bash
docker compose up -d
```

Verify that the container is running:

```bash
docker ps
```

Expected container:

```text
sports-platform-postgres
```

## Database Configuration

| Setting  | Value            |
| -------- | ---------------- |
| Host     | localhost        |
| Port     | 5432             |
| Database | athlete_db       |
| Username | athlete_user     |
| Password | athlete_password |

## Connect with DBeaver

1. Create a new PostgreSQL connection.
2. Enter the database configuration shown above.
3. Test the connection.
4. Browse the `athletes` table under:

```text
athlete_db
└── public
    └── athletes
```

## Run the Microservice

Navigate to:

```text
services/athlete-service
```

Start the application:

```bash
./mvnw spring-boot:run
```

The service will be available at:

```text
http://localhost:8081
```

## Verify the Installation

### Health Check

```http
GET /actuator/health
```

Expected response:

```json
{
  "status": "UP"
}
```

### Create an Athlete

```http
POST /api/v1/athletes
```

```json
{
  "firstName": "Andres",
  "lastName": "Alfaro",
  "email": "andres@example.com",
  "birthDate": "1985-05-20",
  "gender": "MALE"
}
```

Expected response:

* HTTP `201 Created`
* A generated UUID
* `active: true`

## Run Tests

Execute the full test suite:

```bash
./mvnw test
```

## Technology Stack

* Java 21
* Spring Boot
* Hexagonal Architecture
* PostgreSQL
* Docker
* Spring Data JPA
* Hibernate
* OpenAPI
* JUnit 5
* Mockito
* Maven

