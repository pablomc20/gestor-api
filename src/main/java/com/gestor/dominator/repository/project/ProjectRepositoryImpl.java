package com.gestor.dominator.repository.project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.components.ObjectManipulationUtil;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.DbResult;
import com.gestor.dominator.model.postgre.project.CreateProjectRq;
import com.gestor.dominator.model.postgre.project.CreateProjectRs;
import com.gestor.dominator.model.postgre.project.DetailsForClientRs;
import com.gestor.dominator.model.postgre.project.DetailsForEmployeeRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRq;
import com.gestor.dominator.model.postgre.project.ProjectDetailsRs;
import com.gestor.dominator.model.postgre.project.ProjectPayload;
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
        String jsonResult = jdbcTemplate.queryForObject(
                CREATE_PROJECT,
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

        try {

            ProjectPayload projectDetailsRs = jdbcTemplate.queryForObject(
                    GET_PROJECT_DETAILS_BY_ID,
                    this::mapProjectDetails,
                    projectDetailsRq.projectId());

            return ProjectDetailsRs.builder().project(projectDetailsRs).build();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // ********** FUNCIONES AUXILIARES **********

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

    private ProjectPayload mapProjectDetails(ResultSet rs, int rowNum) throws SQLException {
        return new ProjectPayload(
                rs.getString("title"),
                rs.getString("style"),
                rs.getString("size"),
                rs.getString("category"),
                rs.getString("status"),
                rs.getString("chapes"),
                rs.getString("colors"),
                rs.getString("materials"),
                rs.getString("additionals"),
                rs.getDate("start_date").toLocalDate() != null ? rs.getDate("start_date").toLocalDate() : null,
                rs.getDate("end_date").toLocalDate() != null ? rs.getDate("end_date").toLocalDate() : null);
    }

}
