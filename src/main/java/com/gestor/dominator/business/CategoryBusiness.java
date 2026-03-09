package com.gestor.dominator.business;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.category.CategoryRecord;
import com.gestor.dominator.dto.category.CategoryResult;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.mapper.CategoryMapper;
import com.gestor.dominator.model.postgre.category.CategoryRq;
import com.gestor.dominator.repository.category.CategoryRepository;
import com.gestor.dominator.service.category.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryBusiness implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Cacheable("categories")
    @Override
    public List<CategoryResult> getAllCategories() {
        return categoryMapper.toGetAllCategoryResult(categoryRepository.getAllCategories());
    }

    @CacheEvict(value = "categories", allEntries = true)
    @Override
    public CategoryResult createCategory(CategoryRecord categoryRecord) {
        CategoryRq categoryRq = categoryMapper.toRecordRq(categoryRecord);
        return categoryMapper.toCategoryResult(categoryRepository.createCategory(categoryRq));
    }

    @CacheEvict(value = "categories", allEntries = true)
    @Override
    public CategoryResult updateCategory(CategoryRecord categoryRecord, String categoryId) {
        CategoryRq categoryRq = categoryMapper.toRecordRq(categoryRecord);
        return categoryMapper.toCategoryResult(categoryRepository.updateCategory(categoryRq, categoryId));
    }

    @CacheEvict(value = "categories", allEntries = true)
    @Override
    public void deleteCategory(String categoryId) {
        Integer result = categoryRepository.deleteCategory(categoryId);
        if (result == 0) {
            throw new PostgreDbException("Category not found");
        }
    }

}
