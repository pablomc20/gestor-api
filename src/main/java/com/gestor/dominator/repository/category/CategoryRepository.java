package com.gestor.dominator.repository.category;

import java.util.List;

import com.gestor.dominator.model.postgre.category.CategoryRs;
import com.gestor.dominator.model.postgre.category.CategoryRq;

public interface CategoryRepository {
    List<CategoryRs> getAllCategories();

    CategoryRs createCategory(CategoryRq categoryRq);

    CategoryRs updateCategory(CategoryRq categoryRq, String categoryId);

    Integer deleteCategory(String categoryId);
}
