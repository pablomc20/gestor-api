package com.gestor.dominator.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.repository.InteresatedRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InteresatedRepositoryImpl implements InteresatedRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean createInteresated(String email) {
        String sql = """
                INSERT INTO mobile_app_leads (email)
                VALUES (?)
                ON CONFLICT (email)
                DO UPDATE SET
                registration_date = CURRENT_TIMESTAMP
                """;

        return jdbcTemplate.update(sql, email) > 0;
    }

}
