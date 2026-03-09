package com.gestor.dominator.service.category;

import java.util.List;

import com.gestor.dominator.dto.category.CategoryRecord;
import com.gestor.dominator.dto.category.CategoryResult;

public interface CategoryService {
    List<CategoryResult> getAllCategories();

    CategoryResult createCategory(CategoryRecord categoryRecord);

    CategoryResult updateCategory(CategoryRecord categoryRecord, String categoryId);

    void deleteCategory(String categoryId);
}
