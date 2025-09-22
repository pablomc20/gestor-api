package com.gestor.dominator.repository;

import com.gestor.dominator.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategory(String categoryId);

    List<Product> findByTitleContainingIgnoreCase(String title);

    List<Product> findByTagsContaining(String tagId);

    List<Product> findByCategoryAndTitleContainingIgnoreCase(String categoryId, String title);

    List<Product> findByTagsIn(List<String> tagIds);
}