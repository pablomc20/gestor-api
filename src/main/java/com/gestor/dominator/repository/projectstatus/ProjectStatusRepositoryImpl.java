package com.gestor.dominator.repository.projectstatus;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.model.postgre.projectstatus.CreateProjectStatusRq;
import com.gestor.dominator.model.postgre.projectstatus.CreateProjectStatusRs;
import com.gestor.dominator.model.postgre.projectstatus.ProjectStatusRq;
import com.gestor.dominator.model.postgre.projectstatus.ProjectStatusRs;

import static com.gestor.dominator.repository.projectstatus.ProjectStatusQueryBD.*;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProjectStatusRepositoryImpl implements ProjectStatusRepository {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public List<ProjectStatusRs> findProjectStatus(ProjectStatusRq request) {
    return jdbcTemplate.query(FIND_PROJECT_STATUS, this::mapProjectStatusRs, request.projectId());
  }

  @Override
  public CreateProjectStatusRs createProjectStatus(CreateProjectStatusRq request) {
    return jdbcTemplate.queryForObject(INSERT_PROJECT_STATUS, this::mapCreateProjectStatusRs,
        request.projectId(), request.currentStatus(), request.byUserId());
  }

  private ProjectStatusRs mapProjectStatusRs(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
    return new ProjectStatusRs(
        rs.getString("new_status"),
        rs.getString("created_at"));
  }

  private CreateProjectStatusRs mapCreateProjectStatusRs(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
    return CreateProjectStatusRs.builder()
        .statusLogId(rs.getObject("status_log_id", java.util.UUID.class))
        .build();
  }

}
