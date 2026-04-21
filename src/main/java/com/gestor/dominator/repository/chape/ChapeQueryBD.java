package com.gestor.dominator.repository.chape;

public class ChapeQueryBD {
    private ChapeQueryBD() {
    }

    public static final String READ_ALL_CHAPES = "SELECT chape_id, name FROM chapes";
    public static final String CREATE_CHAPE = "INSERT INTO chapes (name) VALUES (?) RETURNING *";
    public static final String UPDATE_CHAPE = "UPDATE chapes SET name = ? WHERE chape_id = ? RETURNING *";
    public static final String DELETE_CHAPE = "DELETE FROM chapes WHERE chape_id = ?";
}
