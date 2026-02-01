package com.gestor.dominator.model.postgre;

public record DbError(
        String code,
        String message) {

}
