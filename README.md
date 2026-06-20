![Java](https://img.shields.io/badge/Java-25-orange)

![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-green)

![Microservices](https://img.shields.io/badge/Microservices-10-blue)

![JWT](https://img.shields.io/badge/JWT-Authentication-red)

![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-brightgreen)

![JUnit5](https://img.shields.io/badge/JUnit-5-success)

![Mockito](https://img.shields.io/badge/Mockito-Testing-blueviolet)

# 🎮 eSports Arena Manager

## 📖 Descripción del Proyecto

**eSports Arena Manager** es una plataforma desarrollada con arquitectura de microservicios para administrar torneos de eSports. El sistema permite gestionar usuarios, juegos, equipos, torneos, partidas, inscripciones, resultados, rankings, notificaciones y sanciones, manteniendo cada dominio desacoplado mediante servicios independientes.

El proyecto fue desarrollado como parte de la **Evaluación Parcial 3** de la asignatura de Microservicios, incorporando los principales componentes del ecosistema Spring Cloud.

---
## 🎯 Objetivo

Desarrollar una plataforma basada en microservicios para administrar torneos de eSports, aplicando buenas prácticas de arquitectura, seguridad, documentación, pruebas unitarias y comunicación entre servicios utilizando el ecosistema Spring.

# 👨‍💻 Integrantes

* Daniel Saa
* Luciano Campos
* Alexis Molina

---

# 🛠 Tecnologías utilizadas

* Java 25
* Spring Boot 4
* Spring Data JPA
* Spring Validation
* Spring Security
* JWT (JSON Web Token)
* Spring Cloud Gateway
* Netflix Eureka Server
* Spring HATEOAS
* SpringDoc OpenAPI (Swagger)
* OpenFeign
* MySQL
* Maven
* JUnit 5
* Mockito
* DataFaker
* Git & GitHub

---

# 🏗 Arquitectura del Proyecto

El sistema está compuesto por los siguientes microservicios:

| Microservicio        | Función                                |
| -------------------- | -------------------------------------- |
| Eureka Server        | Registro y descubrimiento de servicios |
| Gateway Service      | Punto único de entrada a la plataforma |
| User Service         | Gestión de usuarios y autenticación    |
| Game Service         | Gestión de juegos                      |
| Team Service         | Gestión de equipos                     |
| Tournament Service   | Gestión de torneos                     |
| Match Service        | Gestión de partidas                    |
| Registration Service | Gestión de inscripciones               |
| Result Service       | Gestión de resultados                  |
| Ranking Service      | Gestión de rankings                    |
| Notification Service | Gestión de notificaciones              |
| Sanction Service     | Gestión de sanciones                   |

---

# 🚀 Funcionalidades implementadas

## Eureka Server

Se implementó Eureka como servidor de descubrimiento para registrar automáticamente todos los microservicios y permitir su comunicación utilizando nombres de servicio en lugar de direcciones IP.

---

## API Gateway

Se implementó Spring Cloud Gateway como punto único de acceso.

El Gateway enruta las solicitudes hacia:

* Usuarios
* Autenticación (Login / Register)
* Juegos
* Equipos
* Torneos
* Partidas
* Inscripciones
* Resultados
* Rankings
* Notificaciones
* Sanciones

---

## Seguridad

La autenticación fue implementada mediante JWT.

Características:

* Registro de usuarios
* Inicio de sesión
* Generación de Token JWT
* Password cifrada mediante BCrypt
* Spring Security
* Bearer Authentication

La seguridad fue implementada en el **User Service**.

---

## Swagger / OpenAPI

Todos los microservicios fueron documentados mediante SpringDoc OpenAPI, permitiendo visualizar y probar los endpoints desde Swagger UI.

Cada endpoint incluye:

* Descripción
* Parámetros
* Códigos HTTP
* Request
* Response

---

## HATEOAS

Se implementó una segunda versión de la API (V2) utilizando Spring HATEOAS.

Cada controlador V2 devuelve recursos enriquecidos mediante:

* EntityModel
* CollectionModel
* Enlaces Self

Microservicios con versión V2:

* User Service
* Game Service
* Team Service
* Tournament Service
* Match Service
* Registration Service
* Result Service
* Ranking Service
* Notification Service
* Sanction Service

---

## Comunicación entre Microservicios

Se implementó comunicación entre servicios mediante OpenFeign.

Actualmente:

* Tournament Service consume Game Service para validar información relacionada con los juegos antes de realizar determinadas operaciones.

---

# 📦 Microservicios implementados

## 👤 User Service

Funcionalidades:

* CRUD Usuarios
* Login
* Registro
* JWT
* Spring Security
* BCrypt Password Encoder
* Swagger
* HATEOAS V2

---

## 🎮 Game Service

Funcionalidades:

* CRUD Juegos
* Validaciones
* Swagger
* HATEOAS V2

---

## 👥 Team Service

Funcionalidades:

* CRUD Equipos
* Validaciones
* Swagger
* HATEOAS V2

---

## 🏆 Tournament Service

Funcionalidades:

* CRUD Torneos
* Comunicación mediante OpenFeign
* Validaciones
* Swagger
* HATEOAS V2

---

## ⚔ Match Service

Funcionalidades:

* CRUD Partidas
* Validaciones
* Swagger
* HATEOAS V2

---

## 📝 Registration Service

Funcionalidades:

* CRUD Inscripciones
* Validaciones
* Swagger
* HATEOAS V2

---

## 🥇 Result Service

Funcionalidades:

* CRUD Resultados
* Validaciones
* Swagger
* HATEOAS V2

---

## 📈 Ranking Service

Funcionalidades:

* CRUD Rankings
* Validaciones
* Swagger
* HATEOAS V2

---

## 🔔 Notification Service

Funcionalidades:

* CRUD Notificaciones
* Validaciones
* Swagger
* HATEOAS V2

---

## 🚫 Sanction Service

Funcionalidades:

* CRUD Sanciones
* Validaciones
* Swagger
* HATEOAS V2

---

# 🧪 Pruebas Unitarias

Las pruebas unitarias fueron desarrolladas utilizando:

* JUnit 5
* Mockito
* DataFaker

Se implementaron pruebas sobre la lógica de negocio más importante del sistema.
# 🧪 Pruebas Unitarias

Para esta evaluación se implementaron pruebas unitarias utilizando **JUnit 5** y **Mockito**.

## Microservicios con pruebas implementadas

| Microservicio | Clase probada | Framework |
|---------------|---------------|-----------|
| User Service | UsuarioServiceImpl | JUnit 5 + Mockito |
| User Service | AuthService | JUnit 5 + Mockito |
| Tournament Service | TorneoServiceImpl | JUnit 5 + Mockito |

## Total de pruebas

- ✅ UsuarioServiceTest
    - Listar usuarios
    - Buscar por ID
    - Buscar por Email
    - Buscar por Nickname
    - Guardar usuario
    - Actualizar usuario
    - Eliminar usuario
    - Validaciones de excepciones

- ✅ AuthServiceTest
    - Registro exitoso
    - Login exitoso
    - Email duplicado
    - Nickname duplicado
    - Credenciales inválidas

- ✅ TorneoServiceTest
    - CRUD completo
    - Búsquedas
    - Comunicación con Game Service (Mock)
    - Validaciones de excepciones

## Importante

Las pruebas unitarias fueron implementadas únicamente en los siguientes microservicios:

- User Service
- Tournament Service

Los demás microservicios mantienen la misma arquitectura y patrón de implementación, pero no incluyen pruebas unitarias en esta entrega.

### User Service

**UsuarioServiceTest**

* Buscar usuarios
* Buscar por ID
* Buscar por email
* Buscar por nickname
* Crear usuario
* Actualizar usuario
* Eliminar usuario
* Validación de correo duplicado
* Validación de nickname duplicado

**AuthServiceTest**

* Registro exitoso
* Login exitoso
* Login con contraseña incorrecta
* Login con usuario inexistente
* Registro con correo existente
* Registro con nickname existente

### Tournament Service

**TorneoServiceTest**

Pruebas sobre la lógica de negocio del servicio de torneos, incluyendo validaciones y operaciones CRUD.

**Total ejecutado exitosamente: 28 pruebas unitarias.**

---

# 📂 Organización del proyecto

```text
eureka-server
gateway-service
user-service
game-service
team-service
tournament-service
match-service
registration-service
result-service
ranking-service
notification-service
sanction-service
```

---

# ▶️ Ejecución del proyecto

1. Iniciar Eureka Server.
2. Iniciar la base de datos MySQL.
3. Ejecutar los microservicios.
4. Ejecutar Gateway Service.
5. Acceder mediante Gateway.
6. Probar los endpoints utilizando Swagger.

---

# 📋 Características implementadas

| Funcionalidad                  | Estado |
| ------------------------------ | :----: |
| Arquitectura de Microservicios |    ✅   |
| CRUD Completo                  |    ✅   |
| DTO                            |    ✅   |
| Validaciones                   |    ✅   |
| Spring Validation              |    ✅   |
| MySQL                          |    ✅   |
| Spring Data JPA                |    ✅   |
| Swagger / OpenAPI              |    ✅   |
| HATEOAS V2                     |    ✅   |
| JWT                            |    ✅   |
| Spring Security                |    ✅   |
| BCrypt                         |    ✅   |
| OpenFeign                      |    ✅   |
| API Gateway                    |    ✅   |
| Eureka Server                  |    ✅   |
| JUnit 5                        |    ✅   |
| Mockito                        |    ✅   |
| GitHub                         |    ✅   |

---

---

# ✅ Funcionalidades implementadas

## Arquitectura

- ✅ Arquitectura basada en Microservicios
- ✅ Spring Boot 4
- ✅ Spring Cloud
- ✅ API Gateway
- ✅ Eureka Server
- ✅ Descubrimiento automático de servicios

---

## Seguridad

- ✅ JWT Authentication
- ✅ Login
- ✅ Registro de usuarios
- ✅ Password encriptado con BCrypt
- ✅ Roles de usuario
- ✅ Configuración de Spring Security

---

## Persistencia

- ✅ Spring Data JPA
- ✅ MySQL
- ✅ Repositories por microservicio
- ✅ Entidades independientes
- ✅ DTOs
- ✅ Validaciones Bean Validation

---

## APIs

Todos los microservicios implementan operaciones CRUD mediante API REST.

Endpoints implementados:

- Usuarios
- Juegos
- Equipos
- Torneos
- Partidas
- Inscripciones
- Resultados
- Rankings
- Notificaciones
- Sanciones

---

## Documentación

Todos los microservicios poseen documentación mediante:

- ✅ Swagger UI
- ✅ OpenAPI 3

---

## Comunicación entre microservicios

Se implementó comunicación utilizando OpenFeign.

Actualmente:

- Tournament Service → Game Service

---

## HATEOAS

Se implementó una segunda versión de los controladores (V2) utilizando Spring HATEOAS.

Microservicios con HATEOAS:

- ✅ User Service
- ✅ Game Service
- ✅ Team Service
- ✅ Tournament Service
- ✅ Match Service
- ✅ Registration Service
- ✅ Result Service
- ✅ Ranking Service
- ✅ Notification Service
- ✅ Sanction Service

Cada recurso devuelve enlaces de navegación siguiendo el estándar HATEOAS.

---

## Pruebas Unitarias

Las pruebas fueron desarrolladas utilizando:

- JUnit 5
- Mockito
- DataFaker

### Microservicios con pruebas implementadas

### User Service

Clases probadas:

- UsuarioServiceImpl
- AuthService

Casos de prueba:

- Buscar usuario
- Buscar por email
- Buscar por nickname
- Listar usuarios
- Registrar usuario
- Actualizar usuario
- Eliminar usuario
- Login correcto
- Registro correcto
- Email duplicado
- Nickname duplicado
- Credenciales inválidas
- Validaciones de excepciones

---

### Tournament Service

Clase probada:

- TorneoServiceImpl

Casos de prueba:

- CRUD completo
- Buscar por ID
- Buscar por estado
- Buscar por juego
- Actualizar torneo
- Eliminar torneo
- Validaciones de excepciones
- Comunicación con Game Service utilizando Mockito

---

## Importante

Para esta entrega académica, las pruebas unitarias fueron implementadas únicamente en:

- ✅ User Service
- ✅ Tournament Service

Los demás microservicios mantienen la misma arquitectura, estructura y funcionamiento, pero no incluyen pruebas unitarias debido al alcance definido para esta evaluación.

---

# 📌 Estado del proyecto

| Característica | Estado |
|----------------|--------|
| CRUD REST | ✅ |
| DTO | ✅ |
| Validaciones | ✅ |
| Spring Data JPA | ✅ |
| MySQL | ✅ |
| Swagger | ✅ |
| Eureka | ✅ |
| Gateway | ✅ |
| JWT | ✅ |
| Spring Security | ✅ |
| OpenFeign | ✅ |
| HATEOAS | ✅ |
| Mockito | ✅ |
| JUnit 5 | ✅ |

---

# 👨‍💻 Desarrollado por

Daniel Saa

Luciano Campos

Alexis Molina

DUOC UC — Ingeniería en Informática

Asignatura: Microservicios

Evaluación Parcial 3

# 📌 Observaciones

Este proyecto fue desarrollado siguiendo una arquitectura basada en microservicios utilizando el ecosistema Spring Boot y Spring Cloud, aplicando buenas prácticas de desarrollo, documentación, seguridad, pruebas unitarias y comunicación entre servicios.

# 📌 Conclusión

El proyecto permitió implementar una arquitectura de microservicios basada en Spring Boot y Spring Cloud, incorporando autenticación mediante JWT, documentación con Swagger, descubrimiento de servicios con Eureka, centralización mediante API Gateway, comunicación entre microservicios utilizando OpenFeign, representación de recursos con HATEOAS y pruebas unitarias con JUnit 5 y Mockito.

Esta solución demuestra la aplicación de buenas prácticas de desarrollo, separación de responsabilidades y escalabilidad propias de una arquitectura moderna basada en microservicios.