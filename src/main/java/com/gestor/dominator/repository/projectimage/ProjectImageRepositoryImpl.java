package com.gestor.dominator.repository.projectimage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRq;
import com.gestor.dominator.model.postgre.projectimage.CreateProjectImageRs;
import com.gestor.dominator.model.postgre.projectimage.ProjectImageRepository;
import com.gestor.dominator.model.postgre.projectimage.ProjectImageRq;
import com.gestor.dominator.model.postgre.projectimage.ProjectImagesRs;

import lombok.RequiredArgsConstructor;

import static com.gestor.dominator.repository.projectimage.ProjectImageQueryBD.INSERT_PROJECT_IMAGE;

@Repository
@RequiredArgsConstructor
public class ProjectImageRepositoryImpl implements ProjectImageRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public CreateProjectImageRs save(CreateProjectImageRq rq) {
        String type = rq.type() != null ? rq.type() : "INITIAL";
        String visibility = rq.visibility() != null ? rq.visibility() : "PRIVATE";

        UUID projectImageId = jdbcTemplate.queryForObject(
                INSERT_PROJECT_IMAGE,
                UUID.class,
                rq.projectId(),
                rq.imageId(),
                type,
                visibility);

        if (projectImageId == null) {
            throw new PostgreDbException("Error al registrar imagen del proyecto");
        }

        return new CreateProjectImageRs(projectImageId);
    }

    @Override
    public List<ProjectImagesRs> getProjectImages(ProjectImageRq rq) {
        return jdbcTemplate.query(
                ProjectImageQueryBD.GET_PROJECT_IMAGES,
                this::mapProjectImages,
                rq.project_id(),
                rq.type().name()
        );
    }

    private ProjectImagesRs mapProjectImages(ResultSet rs, int rowNum) throws SQLException {
        return new ProjectImagesRs(
                rs.getObject("image_id", UUID.class),
                rs.getString("filename"),
                rs.getString("ext"),
                rs.getString("mimetype"),
                rs.getString("visibility")
        );
    }

}
