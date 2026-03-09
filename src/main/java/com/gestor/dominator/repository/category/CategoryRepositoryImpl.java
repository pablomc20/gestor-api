package com.gestor.dominator.repository.category;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.model.postgre.category.CategoryRq;
import com.gestor.dominator.model.postgre.category.CategoryRs;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CategoryRs> categoryMapper = (rs, rowNum) -> new CategoryRs(
            rs.getString("category_id"),
            rs.getString("name"),
            rs.getString("slug"));

    @Override
    public List<CategoryRs> getAllCategories() {
        return jdbcTemplate.query(
                CategoryQueryBD.READ_ALL_CATEGORIES,
                categoryMapper);
    }

    @Override
    public CategoryRs createCategory(CategoryRq categoryRq) {
        return jdbcTemplate.queryForObject(
                CategoryQueryBD.CREATE_CATEGORY,
                categoryMapper,
                mapCreateCategoryParams(categoryRq));
    }

    @Override
    public CategoryRs updateCategory(CategoryRq categoryRq, String categoryId) {
        return jdbcTemplate.queryForObject(
                CategoryQueryBD.UPDATE_CATEGORY,
                categoryMapper,
                mapUpdateCategoryParams(categoryRq, categoryId));
    }

    @Override
    public Integer deleteCategory(String categoryId) {
        return jdbcTemplate.update(
                CategoryQueryBD.DELETE_CATEGORY,
                mapIdToUUID(categoryId));
    }

    private Object[] mapCreateCategoryParams(CategoryRq categoryRq) {
        return new Object[] { categoryRq.name(), categoryRq.slug() };
    }

    private Object[] mapUpdateCategoryParams(CategoryRq categoryRq, String categoryId) {
        return new Object[] { categoryRq.name(), categoryRq.slug(), mapIdToUUID(categoryId) };
    }

    private Object mapIdToUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw DataValidationException.invalidValue("category_id", id);
        }
    }
}
