package com.gestor.dominator.model.postgre;

public record DbResult<T>(
    String status,
    T data,
    DbError error) {
  public boolean isOk() {
    return "ok".equals(status);
  }
}