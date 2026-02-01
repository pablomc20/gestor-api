package com.gestor.dominator.exceptions.custom;

import org.springframework.http.HttpStatus;

public class FileSystemException extends BaseCustomException {

    protected FileSystemException(String error, String description, HttpStatus status) {
        super(error, description, status);
    }

    public FileSystemException(String description, Throwable cause) {
        super("FILE_SYSTEM_ERROR", description, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }

    public FileSystemException(String description, HttpStatus status) {
        super("FILE_SYSTEM_ERROR", description, status);
    }

}
