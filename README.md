# Esports Arena Manager

## Descripción del proyecto

Esports Arena Manager es una solución backend basada en microservicios desarrollada con Spring Boot.

El sistema permite administrar torneos de esports, usuarios, equipos, juegos, partidas, resultados, rankings, inscripciones, notificaciones y sanciones.

El proyecto fue desarrollado como parte de la Evaluación Parcial 2, cumpliendo con persistencia real, CRUD completo, validaciones, manejo de excepciones y comunicación entre microservicios.

---

## Integrantes

* Daniel Saa
* Integrante 2
* Integrante 3

---

## Tecnologías utilizadas

* Java
* Spring Boot
* Maven
* Spring Data JPA
* Hibernate
* MySQL
* OpenFeign
* Postman
* Git
* GitHub

---

## Arquitectura

El proyecto está organizado en 10 microservicios independientes:

| Microservicio        | Puerto |
| -------------------- | ------ |
| game-service         | 8081   |
| user-service         | 8082   |
| tournament-service   | 8083   |
| team-service         | 8084   |
| match-service        | 8085   |
| result-service       | 8086   |
| registration-service | 8087   |
| ranking-service      | 8088   |
| notification-service | 8089   |
| sanction-service     | 8090   |

---

## Comunicación entre microservicios

La comunicación REST entre microservicios se implementó mediante OpenFeign.

### Flujos implementados

```text
tournament-service -> game-service

team-service -> user-service

match-service -> team-service

registration-service -> tournament-service
registration-service -> team-service

result-service -> match-service

ranking-service -> result-service

sanction-service -> user-service
```

---

## Arquitectura por capas

Cada microservicio utiliza:

```text
Controller
Service
Repository
DTO
Entity
Exception
```

---

## Persistencia

Cada microservicio cuenta con su propia base de datos MySQL independiente.

La persistencia se implementa mediante Spring Data JPA e Hibernate.

---

## Instrucciones de ejecución

### 1. Clonar repositorio

```bash
git clone https://github.com/DanielSNZ/esports-arena-manager.git
```

### 2. Crear bases de datos

```sql
CREATE DATABASE game_service_db;
CREATE DATABASE user_service_db;
CREATE DATABASE tournament_service_db;
CREATE DATABASE team_service_db;
CREATE DATABASE match_service_db;
CREATE DATABASE result_service_db;
CREATE DATABASE registration_service_db;
CREATE DATABASE ranking_service_db;
CREATE DATABASE notification_service_db;
CREATE DATABASE sanction_service_db;
```

### 3. Configurar MySQL

Editar en cada microservicio:

```text
src/main/resources/application.properties
```

y configurar:

```properties
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

---

## Orden recomendado de ejecución

```text
1. user-service
2. game-service
3. tournament-service
4. team-service
5. match-service
6. result-service
7. registration-service
8. ranking-service
9. notification-service
10. sanction-service
```

---

## Endpoints principales

### game-service

```text
GET    /api/v1/juegos
POST   /api/v1/juegos
PUT    /api/v1/juegos/{id}
DELETE /api/v1/juegos/{id}
```

### user-service

```text
GET    /api/v1/usuarios
POST   /api/v1/usuarios
PUT    /api/v1/usuarios/{id}
DELETE /api/v1/usuarios/{id}
```

### tournament-service

```text
GET    /api/v1/torneos
POST   /api/v1/torneos
PUT    /api/v1/torneos/{id}
DELETE /api/v1/torneos/{id}
```

### team-service

```text
GET    /api/v1/equipos
POST   /api/v1/equipos
PUT    /api/v1/equipos/{id}
DELETE /api/v1/equipos/{id}
```

### registration-service

```text
GET    /api/v1/inscripciones
POST   /api/v1/inscripciones
PUT    /api/v1/inscripciones/{id}
DELETE /api/v1/inscripciones/{id}
```

### result-service

```text
GET    /api/v1/resultados
POST   /api/v1/resultados
PUT    /api/v1/resultados/{id}
DELETE /api/v1/resultados/{id}
```

### ranking-service

```text
GET    /api/v1/rankings
POST   /api/v1/rankings
PUT    /api/v1/rankings/{id}
DELETE /api/v1/rankings/{id}
```

### sanction-service

```text
GET    /api/v1/sanciones
POST   /api/v1/sanciones
PUT    /api/v1/sanciones/{id}
DELETE /api/v1/sanciones/{id}
```

---

## Pruebas

Los endpoints fueron probados utilizando Postman.

Se realizaron pruebas CRUD y validaciones entre microservicios utilizando OpenFeign.

---

## Repositorio GitHub

```text
https://github.com/DanielSNZ/esports-arena-manager
```
