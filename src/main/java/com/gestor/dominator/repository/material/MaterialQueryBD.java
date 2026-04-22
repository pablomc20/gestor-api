package com.gestor.dominator.repository.material;

public class MaterialQueryBD {

    public static final String GET_ALL_MATERIALS = "SELECT material_id, name, slug FROM materials";
    public static final String CREATE_MATERIAL = "INSERT INTO materials (name, slug) VALUES (?, ?) RETURNING *";
    public static final String UPDATE_MATERIAL = "UPDATE materials SET name = ?, slug = ? WHERE material_id = ? RETURNING *";
    public static final String DELETE_MATERIAL = "DELETE FROM materials WHERE material_id = ?";
}
