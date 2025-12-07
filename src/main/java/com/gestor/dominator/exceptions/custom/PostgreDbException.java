package com.gestor.dominator.exceptions.custom;

import org.springframework.http.HttpStatus;

public class PostgreDbException extends BaseCustomException {

  public PostgreDbException(String description) {
    super("POSTGRE_DB_ERROR", description, HttpStatus.CONFLICT);
  }

  public PostgreDbException(String description, Throwable t) {
    super("POSTGRE_DB_ERROR", description, HttpStatus.CONFLICT, t);
  }

}
