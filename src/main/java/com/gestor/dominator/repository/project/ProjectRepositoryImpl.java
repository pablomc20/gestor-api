package com.gestor.dominator.repository.project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.components.ObjectManipulationUtil;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.DbResult;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRs;
import com.gestor.dominator.model.postgre.project.DetailsByIdRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRs;
import com.gestor.dominator.model.postgre.project.ProjectPayloadRs;
import com.fasterxml.jackson.core.type.TypeReference;
import static com.gestor.dominator.repository.project.ProjectQueryBD.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectManipulationUtil objectManipulationUtil;

    @Override
    public CreateProjectRs createProject(CreateProjectRq createProjectRq) {
        UUID projectId = Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        CREATE_PROJECT,
                        UUID.class,
                        mapCreateProjectParams(createProjectRq)))
                .orElseThrow(() -> new IllegalStateException("No se pudo crear el proyecto"));

        createProjectImages(projectId, createProjectRq);

        createProjectColors(projectId, createProjectRq);

        createProjectChapes(projectId, createProjectRq);

        createProjectMaterials(projectId, createProjectRq);

        return CreateProjectRs.builder().project_id(projectId).build();
    }

    @Override
    public List<DetailsByIdRs> getProyectClientById(DetailsByIdRq detailsForClientRq) {
        String sql = "SELECT fn_read_client_projects(?)";

        UUID employeeId = detailsForClientRq.projectId();

        String jsonResult = jdbcTemplate.queryForObject(
                sql,
                String.class,
                employeeId);

        DbResult<List<DetailsByIdRs>> detailsListRs = objectManipulationUtil.fromJson(
                jsonResult,
                new TypeReference<DbResult<List<DetailsByIdRs>>>() {
                });

        if (!detailsListRs.isOk()) {
            throw new PostgreDbException(
                    detailsListRs.error().code() + " - " + detailsListRs.error().message());
        }

        return detailsListRs.data();
    }

    @Override
    public ProjectDetailsRs getProjectDetailsById(ProjectDetailsRq projectDetailsRq) {

        try {

            ProjectPayloadRs projectDetailsRs = jdbcTemplate.queryForObject(
                    GET_PROJECT_DETAILS_BY_ID,
                    this::mapProjectDetails,
                    projectDetailsRq.projectId());

            return ProjectDetailsRs.builder().project(projectDetailsRs).build();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public String getStatusById(UUID idProject) {
        try {
            String statusProject = jdbcTemplate.queryForObject(
                    GET_STATUS_BY_ID,
                    String.class,
                    idProject);

            return statusProject;
        } catch (EmptyResultDataAccessException e) {
            return "";
        }
    }

    @Override
    public boolean updateStatus(UUID idProject, String status) {

        if (status == null || status.isEmpty()) {
            return updateCompleteStatus(idProject);
        }

        return jdbcTemplate.update(
                UPDATE_STATUS_PROJECT,
                status,
                idProject) > 0;
    }

    public boolean updateCompleteStatus(UUID idProject) {
        return jdbcTemplate.update(
                UPDATE_COMPLETE_STATUS_PROJECT,
                idProject) > 0;
    }

    // ********** FUNCIONES AUXILIARES **********
    private void createProjectImages(UUID projectId, CreateProjectRq rq) {
        if (rq.images() != null) {
            for (UUID imageId : rq.images()) {
                jdbcTemplate.update(
                        CREATE_PROJECT_IMAGES,
                        imageId,
                        projectId);
            }
        }
    }

    private void createProjectColors(UUID projectId, CreateProjectRq rq) {
        if (rq.colors() != null) {
            for (UUID colorId : rq.colors()) {
                jdbcTemplate.update(
                        CREATE_PROJECT_COLORS,
                        colorId,
                        projectId);
            }
        }
    }

    private void createProjectChapes(UUID projectId, CreateProjectRq rq) {
        if (rq.chapes() != null) {
            for (UUID chapeId : rq.chapes()) {
                jdbcTemplate.update(
                        CREATE_PROJECT_CHAPES,
                        chapeId,
                        projectId);
            }
        }
    }

    private void createProjectMaterials(UUID projectId, CreateProjectRq rq) {
        if (rq.materials() != null) {
            for (UUID materialId : rq.materials()) {
                jdbcTemplate.update(
                        CREATE_PROJECT_MATERIALS,
                        projectId,
                        materialId);
            }
        }
    }

    private Object[] mapCreateProjectParams(CreateProjectRq r) {
        return new Object[] {
                r.title(),
                r.size(),
                r.style(),
                r.additionalInfo(),
                r.startDate(),
                r.estimatedCompletionDate(),
                r.actualCompletionDate(), // Parámetro faltante agregado
                r.budget(),
                r.category(),
                r.client(),
                r.employee()
        };
    }

    private ProjectPayloadRs mapProjectDetails(ResultSet rs, int rowNum) throws SQLException {
        return new ProjectPayloadRs(
                rs.getString("user_client"),
                rs.getString("user_employee"),
                rs.getString("title"),
                rs.getString("style"),
                rs.getString("size"),
                rs.getString("category"),
                rs.getString("status"),
                rs.getString("chapes"),
                rs.getString("colors"),
                rs.getString("materials"),
                rs.getString("additionals"),
                rs.getDate("real_end_date") != null ? rs.getDate("real_end_date").toLocalDate() : null,
                rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null,
                rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null);
    }
}
