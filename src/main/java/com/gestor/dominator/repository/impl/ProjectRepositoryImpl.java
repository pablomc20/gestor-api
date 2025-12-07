package com.gestor.dominator.repository.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.components.ObjectManipulationUtil;
import com.gestor.dominator.dto.projects.DetailsForEmployeeRecord;
import com.gestor.dominator.dto.projects.DetailsForEmployeeResult;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRq;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRs;
import com.gestor.dominator.repository.ProjectRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ObjectManipulationUtil objectManipulationUtil;

  @Override
  public CreateProjectRs createProject(CreateProjectRq createProjectRecord) {
    String sql = "SELECT fn_create_new_project(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    String jsonResult = jdbcTemplate.queryForObject(
        sql,
        String.class,
        rowMapperProject(createProjectRecord));

    CreateProjectRs createProjectResult = objectManipulationUtil
        .objectMapperToString(jsonResult, CreateProjectRs.class);

    if ("error".equals(createProjectResult.status())) {
      throw new PostgreDbException(createProjectResult.error_code() + " - " + createProjectResult.error_message());
    }

    return createProjectResult;
  }

  @Override
  public List<DetailsForEmployeeRs> getProyectClientById(DetailsForEmployeeRq detailsForClientRq) {
    String sql = "SELECT * FROM fn_read_employee_projects(?)";

    List<DetailsForEmployeeRs> detailsForClientRsList = jdbcTemplate.query(
        sql,
        ps -> ps.setObject(1, detailsForClientRq.projectId()),
        DETAILS_ROW_MAPPER);

    return detailsForClientRsList;
  }

  private Object[] rowMapperProject(CreateProjectRq r) {
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

  private static final RowMapper<DetailsForEmployeeRs> DETAILS_ROW_MAPPER = (rs, rowNum) -> DetailsForEmployeeRs
      .builder()
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
