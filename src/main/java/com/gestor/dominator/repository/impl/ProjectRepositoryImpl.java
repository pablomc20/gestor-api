package com.gestor.dominator.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.components.ObjectManipulationUtil;
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
        createProjectRecord.employee(),
        createProjectRecord.client(),
        createProjectRecord.title(),
        createProjectRecord.description(),
        createProjectRecord.size(),
        createProjectRecord.startDate(),
        createProjectRecord.estimatedCompletionDate(),
        createProjectRecord.budget(),
        createProjectRecord.category(),
        createProjectRecord.colors(),
        createProjectRecord.materials(),
        createProjectRecord.images());

    CreateProjectResult createProjectResult = objectManipulationUtil
        .objectMapperToString(jsonResult, CreateProjectResult.class);

    if ("error".equals(createProjectResult.status())) {
      throw new PostgreDbException(createProjectResult.error_code() + " - " + createProjectResult.error_message());
    }

    return createProjectResult;
  }

}
