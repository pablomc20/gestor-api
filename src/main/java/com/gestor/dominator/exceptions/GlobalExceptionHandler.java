package com.gestor.dominator.exceptions;

import com.gestor.dominator.dto.ErrorResponse;
import com.gestor.dominator.exceptions.custom.BaseCustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para toda la aplicación.
 * Captura y procesa todas las excepciones, devolviendo respuestas
 * estandarizadas.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Maneja todas las excepciones personalizadas que extienden BaseCustomException
     */
    @ExceptionHandler(BaseCustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(BaseCustomException ex) {
        logger.error("Excepción personalizada: {} - {}", ex.getError(), ex.getDescription(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getError(),
                ex.getDescription(),
                ex.getStatusCode());

        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException ex) {

        // Puedes revisar si adentro viene InvalidFormatException
        Throwable cause = ex.getCause();
        if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException ife) {
            if (ife.getTargetType() == java.util.UUID.class) {
                ErrorResponse errorResponse = new ErrorResponse(
                        "UUID_INVALID",
                        "Uno o más UUID no tienen un formato válido.",
                        HttpStatus.BAD_REQUEST.value());
                return ResponseEntity.badRequest().body(errorResponse);
            }
        }

        ErrorResponse errorResponse = new ErrorResponse(
                "BAD_JSON",
                "El cuerpo JSON no es válido.",
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointer(NullPointerException ex) {
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .error("NULL_FIELD")
                        .description("El JSON contiene campos faltantes o nulos donde no deberían estar.")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build());
    }

    /**
     * Maneja errores de validación de @Valid (MethodArgumentNotValidException)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String description = "Errores de validación: " + errors.toString();
        logger.warn("Errores de validación: {}", errors);

        ErrorResponse errorResponse = new ErrorResponse(
                "VALIDATION_ERROR",
                description,
                HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Maneja errores de autenticación de Spring Security
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        logger.warn("Intento de autenticación fallido: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "AUTHENTICATION_ERROR",
                "Credenciales inválidas",
                HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja errores de usuario no encontrado
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        logger.warn("Usuario no encontrado: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "AUTHENTICATION_ERROR",
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja errores de acceso denegado
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        logger.warn("Acceso denegado: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "AUTHORIZATION_ERROR",
                "Acceso denegado: no tienes permisos suficientes",
                HttpStatus.FORBIDDEN.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Maneja errores de tamaño de archivo excedido en uploads
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        logger.warn("Archivo demasiado grande: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "FILE_UPLOAD_ERROR",
                "El archivo excede el tamaño máximo permitido",
                HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        logger.warn("Recurso no encontrado: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "RESOURCE_NOT_FOUND",
                "El recurso solicitado no se encuentra",
                HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(HttpRequestMethodNotSupportedException ex) {
        logger.warn("Método de solicitud no soportado: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "METHOD_NOT_ALLOWED",
                "El método de solicitud no está permitido",
                HttpStatus.METHOD_NOT_ALLOWED.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse> handleInternalAuthenticationServiceException(
            InternalAuthenticationServiceException ex) {
        logger.warn("Error de autenticación interna: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "AUTHENTICATION_ERROR",
                "Error interno de autenticación",
                HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja cualquier otra excepción no capturada
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Excepción no manejada: {}", ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(
                "INTERNAL_SERVER_ERROR",
                "Ha ocurrido un error interno del servidor",
                HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}