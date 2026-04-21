# Agent Guide: Gestor API (Dominator)

High-signal guidance for OpenCode agents working on this repository.

## Architecture & Workflow

- **Database-First Logic:** Most complex operations (e.g., project creation) are handled via PostgreSQL functions (e.g., `fn_create_new_project`).
- **Repository Pattern:** Custom repository implementations (e.g., `ProjectRepositoryImpl`) use `JdbcTemplate` to call these functions. They expect a `DbResult<T>` JSON response, which is parsed using `ObjectManipulationUtil`.
- **Business Layer:** Split between `*Business` services (orchestrators) and `*UseCase` classes (specific logic). Always check `src/main/java/com/gestor/dominator/business/` and subfolders.
- **DTOs & Mappers:** Heavy use of Java Records for DTOs and MapStruct for mapping. Generated mappers are in `target/generated-sources/annotations`.

## Critical Commands

- **Build & Install:** `./mvnw clean install` (required to generate MapStruct implementations).
- **Run Local:** `./mvnw spring-boot:run` (defaults to `dev` profile).
- **Tests:** `./mvnw test` (Note: Minimal existing tests).

## Development Gotchas

- **JSON Results:** Repositories often receive raw JSON strings from DB functions. Use `objectManipulationUtil.fromJson(json, new TypeReference<DbResult<T>>(){})` to parse them.
- **Error Handling:** Database errors are returned as part of `DbResult`. Repositories must check `result.isOk()` and throw `PostgreDbException` if false.
- **Constants:** Project status flow is managed in `com.gestor.dominator.constants.StatusProject`.
- **UUIDs:** The system uses `UUID` extensively. Incorrect formats trigger a specific `UUID_INVALID` error in `GlobalExceptionHandler`.

## Environment Prerequisites

The following environment variables must be configured (see `GEMINI.md` for full list):
- `POSTGRE_SQL_URI`, `MONGODB_URI`, `JWT_SECRET`, `MINIO_ENDPOINT`, `CLIENT_DIPOMEX_URL`.
