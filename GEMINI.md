# Proyecto Carpinteria - Gestor API

## Descripción General
**Carpinteria** es una API REST desarrollada con **Java 21** y **Spring Boot 3.5.x**. Es un sistema de gestión que maneja categorías, materiales, proyectos, pagos, usuarios e interesados, integrando múltiples bases de datos y servicios de almacenamiento externos.

## Stack Tecnológico
- **Lenguaje:** Java 21
- **Framework Principal:** Spring Boot 3.5.10
- **Gestión de Dependencias:** Maven
- **Bases de Datos:** 
  - **PostgreSQL:** Almacenamiento relacional principal (vía Spring Data JPA).
  - **MongoDB:** Almacenamiento de documentos (configurado en `application-dev.properties`).
- **Seguridad:** Spring Security con **JWT** (JSON Web Tokens).
- **Almacenamiento de Archivos:** **Minio** (Compatible con S3).
- **Documentación:** Springdoc OpenAPI / **Swagger**.
- **Herramientas de Productividad:** 
  - **Lombok:** Reducción de código boilerplate.
  - **MapStruct:** Mapeo eficiente entre Entidades y DTOs.
- **Integraciones Externas:**
  - **OpenFeign:** Para consumo de APIs externas (ej. Dipomex para códigos postales).
  - **Thumbnailator:** Optimización y generación de miniaturas de imágenes.

## Arquitectura y Convenciones
El proyecto sigue una arquitectura por capas bien definida:
- **`controller`**: Puntos de entrada de la API REST.
- **`business`**: Capa superior de lógica de negocio (casos de uso).
- **`service`**: Servicios de dominio que interactúan con los repositorios.
- **`repository`**: Acceso a datos, incluyendo implementaciones personalizadas (`CategoryRepositoryImpl`, etc.).
- **`dto`**: Objetos de Transferencia de Datos para peticiones y respuestas.
- **`mapper`**: Interfaces MapStruct para la conversión entre modelos y DTOs.
- **`model`**: Entidades JPA (PostgreSQL) y modelos de datos.
- **`client`**: Clientes para servicios externos (Dipomex, Minio).
- **`config`**: Configuraciones de Seguridad, Swagger, Minio y Bean definitions.
- **`components`**: Utilidades transversales y componentes de sistema.

## Comandos de Desarrollo
- **Construcción del proyecto:**
  ```bash
  ./mvnw clean install
  ```
- **Ejecutar la aplicación (Local):**
  ```bash
  ./mvnw spring-boot:run
  ```
- **Ejecutar con Docker:**
  ```bash
  docker-compose up --build
  ```
- **Ejecutar Pruebas:**
  ```bash
  ./mvnw test
  ```

## Configuración y Variables de Entorno
La aplicación requiere las siguientes variables de entorno (pueden definirse en un archivo `.env` o en el sistema):
- `POSTGRE_SQL_URI`: URL de conexión a PostgreSQL.
- `POSTGRE_SQL_USER` / `POSTGRE_SQL_PASSWORD`: Credenciales de la BD.
- `MONGODB_URI`: URL de conexión a MongoDB.
- `JWT_SECRET`: Clave secreta para la firma de tokens JWT.
- `MINIO_ENDPOINT`, `MINIO_ACCESS_KEY`, `MINIO_SECRET_KEY`: Configuración del bucket de Minio.
- `CLIENT_DIPOMEX_URL` / `CLIENT_DIPOMEX_TOKEN`: Credenciales para el cliente Dipomex.

## Notas Adicionales
- La documentación de Swagger suele estar disponible en: `http://localhost:8080/swagger-ui/index.html` (o el puerto configurado).
- Los archivos generados por MapStruct se encuentran en `target/generated-sources/annotations`.
- Se utiliza el perfil de desarrollo `dev` por defecto o configurado en `application-dev.properties`.
