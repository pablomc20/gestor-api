package com.gestor.dominator.repository.projectuser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.model.postgre.projectuser.ProjectUserRq;
import com.gestor.dominator.model.postgre.projectuser.ProjectUserRs;

import static com.gestor.dominator.repository.projectuser.ProjectUserQueryBD.*;

@Repository
public class ProjectUserRepositoryImpl implements ProjectUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProjectUserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ProjectUserRs> findProjectsByEmployeeAndType(ProjectUserRq request) {
        String sql = switch (request.type()) {
            case NEW -> GET_PROJECTS_FOR_EMPLOYEE_NEW;
            case IN_COURSE -> GET_PROJECTS_FOR_EMPLOYEE_IN_COURSE;
            case FINISHED -> GET_PROJECTS_FOR_EMPLOYEE_FINISHED;
        };

        return jdbcTemplate.query(sql, this::mapProjectUserRs, request.userId());
    }

    @Override
    public List<ProjectUserRs> findProjectsByClientAndType(ProjectUserRq request) {
        String sql = switch (request.type()) {
            case NEW -> GET_PROJECTS_FOR_CLIENT_NEW; // Assuming similar queries for clients
            case IN_COURSE -> GET_PROJECTS_FOR_CLIENT_IN_COURSE; // Placeholder, replace with actual query
            case FINISHED -> GET_PROJECTS_FOR_CLIENT_FINISHED; // Placeholder, replace with actual query
        };

        return jdbcTemplate.query(sql, this::mapProjectUserRs, request.userId());
    }    

    private ProjectUserRs mapProjectUserRs(ResultSet rs, int rowNum) throws SQLException {
        Integer daysRemaining = rs.getObject("dias_restantes") != null ? rs.getInt("dias_restantes") : null;

        return new ProjectUserRs(
                rs.getString("project_id"),
                rs.getString("title"),
                rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null,
                rs.getDate("estimated_completion_date") != null ? rs.getDate("estimated_completion_date").toLocalDate() : null,
                rs.getDate("actual_completion_date") != null ? rs.getDate("actual_completion_date").toLocalDate() : null,
                rs.getTimestamp("last_status_date") != null ? rs.getTimestamp("last_status_date").toLocalDateTime() : null,
                daysRemaining,
                rs.getString("client_name"),
                rs.getString("phone"),
                rs.getString("status"),
                rs.getString("user_id")
        );
    }

}
