package com.gestor.dominator.repository;

import com.gestor.dominator.dto.product.ProductWithReferencesResponse;
import com.gestor.dominator.model.Product;
import org.bson.types.ObjectId;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, ObjectId> {

    List<Product> findByCategory(ObjectId categoryId);

    List<Product> findByTitleContainingIgnoreCase(String title);

    List<Product> findByTagsContaining(ObjectId tagId);

    List<Product> findByCategoryAndTitleContainingIgnoreCase(ObjectId categoryId, String title);

    List<Product> findByTagsIn(List<ObjectId> tagIds);

    @Aggregation(pipeline = {
        "{ $lookup: { from: 'category', localField: 'category', foreignField: '_id', as: 'category' } }",
        "{ $unwind: { path: '$category', preserveNullAndEmptyArrays: true } }",
        "{ $lookup: { from: 'tags', localField: 'tags', foreignField: '_id', as: 'tags' } }",
        "{ $lookup: { from: 'images', localField: 'images', foreignField: '_id', as: 'images' } }"
    })
    List<ProductWithReferencesResponse> findAllWithRelations();
}