package com.gestor.dominator.repository.projectimage;

public final class ProjectImageQueryBD {

    private ProjectImageQueryBD() {
    }

    public static final String INSERT_PROJECT_IMAGE = """
            INSERT INTO project_images (project_id, image_id, type, visibility)
            VALUES (?, ?, ?::image_type, ?::image_visibility)
            RETURNING project_image_id;
            """;

    public static final String GET_PROJECT_IMAGES = """
                select i.image_id, i.filename, i.ext, i.mimetype, pi.visibility  from
                images i 
                inner join project_images pi on pi.image_id  = i.image_id 
                where pi.project_id = ?
                and pi."type" =  ?::image_type;
            """;
}
