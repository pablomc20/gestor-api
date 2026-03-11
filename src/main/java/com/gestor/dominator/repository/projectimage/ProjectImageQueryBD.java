package com.gestor.dominator.repository.projectimage;

public final class ProjectImageQueryBD {

    private ProjectImageQueryBD() {
    }

    public static final String INSERT_PROJECT_IMAGE = """
            INSERT INTO project_images (project_id, image_id, type, visibility)
            VALUES (?, ?, ?::image_type, ?::image_visibility)
            RETURNING project_image_id;
            """;
}
