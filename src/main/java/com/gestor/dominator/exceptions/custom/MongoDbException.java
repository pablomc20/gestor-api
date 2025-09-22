package com.gestor.dominator.exceptions.custom;

import org.springframework.http.HttpStatus;

/**
 * Excepción personalizada para errores relacionados con operaciones de MongoDB.
 * Se utiliza cuando hay problemas de conexión, consultas fallidas o errores de persistencia.
 */
public class MongoDbException extends BaseCustomException {

    public MongoDbException(String description) {
        super("MONGODB_ERROR", description, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public MongoDbException(String description, Throwable cause) {
        super("MONGODB_ERROR", description, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }

    public static MongoDbException connectionError(String details) {
        return new MongoDbException("Error de conexión con MongoDB: " + details);
    }

    public static MongoDbException queryError(String operation, String details) {
        return new MongoDbException("Error en operación de MongoDB '" + operation + "': " + details);
    }

    public static MongoDbException saveError(String entityType, String details) {
        return new MongoDbException("Error al guardar " + entityType + ": " + details);
    }

    public static MongoDbException findError(String entityType, String id) {
        return new MongoDbException("Error al buscar " + entityType + " con ID '" + id + "'");
    }

    public static MongoDbException deleteError(String entityType, String id) {
        return new MongoDbException("Error al eliminar " + entityType + " con ID '" + id + "'");
    }
}