package com.gestor.dominator.components;

import org.bson.types.ObjectId;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.gestor.dominator.exceptions.custom.DataValidationException;

/**
 * Utilidad para conversiones entre String y ObjectId
 * Maneja la conversión automática en Spring Data MongoDB
 */
@Component
public class ObjectIdConverter {

    /**
     * Convierte un String a ObjectId
     * @param id String representation del ObjectId
     * @return ObjectId o null si el string es vacío
     * @throws DataValidationException si el formato es inválido
     */
    public ObjectId stringToObjectId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return null;
        }
        try {
            return new ObjectId(id);
        } catch (IllegalArgumentException e) {
            throw new DataValidationException("ID inválido: " + id);
        }
    }

    /**
     * Convierte un ObjectId a String
     * @param objectId ObjectId a convertir
     * @return String representation o null si es null
     */
    public String objectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }

    /**
     * Valida si un String es un ObjectId válido
     * @param id String a validar
     * @return true si es válido, false en caso contrario
     */
    public boolean isValidObjectId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        try {
            new ObjectId(id);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Converter para Spring Data MongoDB - String to ObjectId
     */
    public static class StringToObjectIdConverter implements Converter<String, ObjectId> {
        @Override
        public ObjectId convert(String source) {
            if (source == null || source.trim().isEmpty()) {
                return null;
            }
            try {
                return new ObjectId(source);
            } catch (IllegalArgumentException e) {
                throw new DataValidationException("ID inválido: " + source);
            }
        }
    }

    /**
     * Converter para Spring Data MongoDB - ObjectId to String
     */
    public static class ObjectIdToStringConverter implements Converter<ObjectId, String> {
        @Override
        public String convert(ObjectId source) {
            return source != null ? source.toHexString() : null;
        }
    }
}