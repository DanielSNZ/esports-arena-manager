# eSports Arena Manager

## Descripción

eSports Arena Manager es un sistema basado en arquitectura de microservicios desarrollado con Spring Boot para administrar torneos de eSports.

El proyecto permite gestionar usuarios, juegos, equipos, torneos, partidas, rankings, resultados, sanciones, notificaciones e inscripciones, utilizando comunicación entre microservicios mediante OpenFeign, descubrimiento de servicios con Eureka y un API Gateway como punto único de entrada.

---

## Integrantes

* Daniel Saa
* (Agregar los demás integrantes)

---

## Tecnologías utilizadas

* Java 25
* Spring Boot
* Spring Cloud
* Spring Data JPA
* Spring Security + JWT
* Spring Cloud Gateway
* Eureka Server
* OpenFeign
* MySQL
* Swagger / OpenAPI
* JUnit 5
* Mockito
* Maven
* Git / GitHub

---

## Microservicios

| Microservicio        | Puerto |
| -------------------- | ------ |
| Eureka Server        | 8761   |
| API Gateway          | 8080   |
| User Service         | 8082   |
| Tournament Service   | 8083   |
| Game Service         | 8084   |
| Team Service         | 8085   |
| Match Service        | 8086   |
| Registration Service | 8087   |
| Result Service       | 8088   |
| Ranking Service      | 8089   |
| Notification Service | 8090   |
| Sanction Service     | 8091   |

---

## Rutas principales del Gateway

| Ruta                   |
| ---------------------- |
| /api/v1/usuarios       |
| /api/v1/juegos         |
| /api/v1/equipos        |
| /api/v1/torneos        |
| /api/v1/partidas       |
| /api/v1/inscripciones  |
| /api/v1/resultados     |
| /api/v1/rankings       |
| /api/v1/notificaciones |
| /api/v1/sanciones      |

---

## Swagger

Cada microservicio dispone de documentación OpenAPI mediante Swagger.

Ejemplos:

* http://localhost:8082/swagger-ui/index.html
* http://localhost:8083/swagger-ui/index.html

---

## Arquitectura

* Eureka Server para descubrimiento de servicios.
* API Gateway como punto único de acceso.
* Comunicación entre microservicios mediante OpenFeign.
* Persistencia con MySQL.
* Autenticación mediante JWT.
* Pruebas unitarias con JUnit 5 y Mockito.

---

## Ejecución

1. Levantar Eureka Server.
2. Levantar API Gateway.
3. Levantar los microservicios.
4. Acceder a Swagger.
5. Registrar un usuario.
6. Iniciar sesión.
7. Consumir los endpoints protegidos con JWT.

---

## Repositorio

GitHub:

https://github.com/DanielSNZ/esports-arena-manager
