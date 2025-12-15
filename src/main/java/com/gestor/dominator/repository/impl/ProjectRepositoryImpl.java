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
import com.gestor.dominator.model.postgre.DbResult;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsForClientRs;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRq;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRs;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRs;
import com.gestor.dominator.repository.ProjectRepository;

import com.fasterxml.jackson.core.type.TypeReference;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ObjectManipulationUtil objectManipulationUtil;

  private static final String FN_CREATE_PROJECT = "SELECT fn_create_new_project(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  @Override
  public CreateProjectRs createProject(CreateProjectRq createProjectRq) {
    String jsonResult = jdbcTemplate.queryForObject(
        FN_CREATE_PROJECT,
        String.class,
        mapCreateProjectParams(createProjectRq));

    DbResult<CreateProjectRs> result = objectManipulationUtil.fromJson(
        jsonResult,
        new TypeReference<>() {
        });

    if (!result.isOk()) {
      throw new PostgreDbException(
          result.error().code() + " - " + result.error().message());
    }

    return result.data();
  }

  @Override
  public List<DetailsForClientRs> getProyectClientById(DetailsForEmployeeRq detailsForClientRq) {
    String sql = "SELECT fn_read_client_projects(?)";

    UUID employeeId = detailsForClientRq.projectId();

    String jsonResult = jdbcTemplate.queryForObject(
        sql,
        String.class,
        employeeId);

    DbResult<List<DetailsForClientRs>> detailsListRs = objectManipulationUtil.fromJson(
        jsonResult,
        new TypeReference<DbResult<List<DetailsForClientRs>>>() {
        });

    if (!detailsListRs.isOk()) {
      throw new PostgreDbException(
          detailsListRs.error().code() + " - " + detailsListRs.error().message());
    }

    return detailsListRs.data();
  }

  @Override
  public ProjectDetailsRs getProjectDetailsById(ProjectDetailsRq projectDetailsRq) {
    String sql = "SELECT * FROM fn_read_project_details(?)";

    String jsonResult = jdbcTemplate.queryForObject(
        sql,
        String.class,
        projectDetailsRq.projectId());

    DbResult<ProjectDetailsRs> projectDetailsRs = objectManipulationUtil.fromJson(
        jsonResult,
        new TypeReference<DbResult<ProjectDetailsRs>>() {
        });

    if (!projectDetailsRs.isOk()) {
      throw new PostgreDbException(
          projectDetailsRs.error().code() + " - " + projectDetailsRs.error().message());
    }

    return projectDetailsRs.data();
  }

  private Object[] mapCreateProjectParams(CreateProjectRq r) {
    return new Object[] {
        r.employee(),
        r.client(),
        r.title(),
        r.size(),
        r.style(),
        r.additionalInfo(),
        r.startDate(),
        r.estimatedCompletionDate(),
        r.budget(),
        r.category(),
        r.colors(),
        r.materials(),
        r.images(),
        r.chapes()
    };
  }

}
