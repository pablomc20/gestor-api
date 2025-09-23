package com.gestor.dominator.module;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long>, CustomizedUserRepository {
    // Author findByUsername(String username);
    @Aggregation(pipeline = {
        "{ $lookup: { from: 'profiles', localField: 'profileId', foreignField: '_id', as: 'profile' } }",
        "{ $unwind: { path: '$profile', preserveNullAndEmptyArrays: true } }"
    })
    List<AuthorWithProfile> findAllWithProfiles();
}
