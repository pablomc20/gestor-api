package com.gestor.dominator.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.category.CategoryRecord;
import com.gestor.dominator.dto.category.CategoryResult;
import com.gestor.dominator.service.category.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResult>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<CategoryResult> createCategory(@RequestBody CategoryRecord categoryRecord) {
        CategoryResult result = categoryService.createCategory(categoryRecord);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryResult> updateCategory(@PathVariable String categoryId,
            @RequestBody CategoryRecord categoryRecord) {
        return ResponseEntity.ok(categoryService.updateCategory(categoryRecord, categoryId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CategoryResult> deleteCategory(@PathVariable String categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
