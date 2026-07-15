package com.gestor.dominator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.gestor.dominator.model.postgre.auth.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
        SELECT u FROM User u
        LEFT JOIN FETCH u.userDetail ud
        WHERE u.email = :identifier
        OR ud.phone = :identifier
    """)
    Optional<User> findByEmailOrPhone(@Param("identifier") String identifier);

}