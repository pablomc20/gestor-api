package com.gestor.dominator.repository.category;

public class CategoryQueryBD {
    private CategoryQueryBD() {
    }

    public static final String READ_ALL_CATEGORIES = "SELECT category_id, name, slug FROM categories";
    public static final String CREATE_CATEGORY = "INSERT INTO categories (name, slug) VALUES (?, ?) RETURNING *";
    public static final String UPDATE_CATEGORY = "UPDATE categories SET name = ?, slug = ? WHERE category_id = ? RETURNING *";
    public static final String DELETE_CATEGORY = "DELETE FROM categories WHERE category_id = ?";
}
