package com.gestor.dominator.service;

import java.util.List;
import java.util.Optional;

import com.gestor.dominator.dto.product.ProductRequest;
import com.gestor.dominator.dto.product.ProductResponse;
import com.gestor.dominator.dto.product.ProductWithReferencesResponse;

public interface ProductService {
    List<ProductWithReferencesResponse> getAllProducts();

    Optional<ProductResponse> getProductById(String id);

    List<ProductResponse> getProductsByCategory(String categoryId);

    List<ProductResponse> searchProducts(String title);

    List<ProductResponse> getProductsByTag(String tagId);

    ProductResponse createProduct(ProductRequest request);

    Optional<ProductResponse> updateProduct(String id, ProductRequest request);

    boolean deleteProduct(String id);
}