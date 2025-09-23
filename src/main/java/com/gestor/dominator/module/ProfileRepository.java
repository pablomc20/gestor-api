package com.gestor.dominator.module;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {
    
}
