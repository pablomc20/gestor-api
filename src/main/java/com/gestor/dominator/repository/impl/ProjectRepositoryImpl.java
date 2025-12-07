package com.gestor.dominator.repository.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.components.ObjectManipulationUtil;
import com.gestor.dominator.dto.projects.DetailsForClientRq;
import com.gestor.dominator.dto.projects.DetailsForClientRs;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.project.CreateProjectRecord;
import com.gestor.dominator.model.postgre.project.CreateProjectResult;
import com.gestor.dominator.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ObjectManipulationUtil objectManipulationUtil;

  @Override
  public CreateProjectResult createProject(CreateProjectRecord createProjectRecord) {
    String sql = "SELECT fn_create_new_project(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    String jsonResult = jdbcTemplate.queryForObject(
        sql,
        String.class,
        rowMapperProject(createProjectRecord));

    CreateProjectResult createProjectResult = objectManipulationUtil
        .objectMapperToString(jsonResult, CreateProjectResult.class);

    if ("error".equals(createProjectResult.status())) {
      throw new PostgreDbException(createProjectResult.error_code() + " - " + createProjectResult.error_message());
    }

    return createProjectResult;
  }

  @Override
  public List<DetailsForClientRs> getProyectClientById(DetailsForClientRq detailsForClientRq) {
    String sql = "SELECT * FROM fn_read_employee_projects(?)";

    List<DetailsForClientRs> detailsForClientRsList = jdbcTemplate.query(
        sql,
        ps -> ps.setObject(1, detailsForClientRq.projectId()),
        DETAILS_ROW_MAPPER);

    return detailsForClientRsList;
  }

  private Object[] rowMapperProject(CreateProjectRecord r) {
    return new Object[] {
        r.employee(),
        r.client(),
        r.title(),
        r.description(),
        r.size(),
        r.startDate(),
        r.estimatedCompletionDate(),
        r.budget(),
        r.category(),
        r.colors(),
        r.materials(),
        r.images()
    };
  }

  private static final RowMapper<DetailsForClientRs> DETAILS_ROW_MAPPER = (rs, rowNum) -> DetailsForClientRs.builder()
      .title(rs.getString("title"))
      .startDate(rs.getObject("started", LocalDate.class))
      .estimatedCompletionDate(rs.getObject("estimated", LocalDate.class))
      .daysDifference(rs.getInt("days_difference"))
      .name(rs.getString("name"))
      .status(rs.getString("status"))
      .projectId(rs.getObject("project_id", UUID.class))
      .userId(rs.getObject("user_id", UUID.class))
      .type(rs.getString("type_desc"))
      .build();

}
