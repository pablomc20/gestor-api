package com.gestor.dominator.repository.project;

public final class ProjectQueryBD {

    private ProjectQueryBD() {
    }

    public static final String GET_PROJECT_DETAILS_BY_ID = """
            SELECT p.user_client, p.user_employee, p.title, p.size, p.style, p.additionals, ca.name as category, p.status,
                p.start_date, p.estimated_completion_date as end_date, p.actual_completion_date as real_end_date,
                (select STRING_AGG(name, ', ' ORDER BY name)
                    from materials m inner join project_materials pm on pm.material_id = m.material_id
                    where pm.project_id = p.project_id) as materials,
                (select STRING_AGG(name, ', ' ORDER BY name)
                    from colors c inner join project_colors pc on pc.color_id = c.color_id
                    where pc.project_id = p.project_id) as colors,
                (select STRING_AGG(name, ', ' ORDER BY name)
                    from chapes c inner join project_chapes pc on pc.chape_id = c.chape_id
                    where pc.project_id = p.project_id) as chapes
                FROM projects p
                INNER JOIN categories ca ON ca.category_id = p.category_id
                WHERE p.project_id = ?;
            """;

    public static final String CREATE_PROJECT = """
                INSERT INTO projects (
                    title, size, style, additionals, start_date, estimated_completion_date, actual_completion_date,
                    budget, category_id, user_client, user_employee
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING project_id;
            """;

    public static final String CREATE_PROJECT_MATERIALS = """
                INSERT INTO project_materials (project_id, material_id)
                VALUES (?, ?);
            """;

    public static final String CREATE_PROJECT_IMAGES = """
                INSERT INTO project_images (project_id, image_id)
                VALUES (?, ?);
            """;

    public static final String CREATE_PROJECT_COLORS = """
                INSERT INTO project_colors (project_id, color_id)
                VALUES (?, ?);
            """;

    public static final String CREATE_PROJECT_CHAPES = """
                INSERT INTO project_chapes (project_id, chape_id)
                VALUES (?, ?);
            """;

    public static final String GET_STATUS_BY_ID = """
                SELECT status FROM projects WHERE project_id = ?;
            """;

    public static final String UPDATE_STATUS_PROJECT = """
                UPDATE projects SET status = ?::project_status, updated_at = CURRENT_TIMESTAMP
                WHERE project_id = ?;
            """;

    public static final String UPDATE_COMPLETE_STATUS_PROJECT = """
                UPDATE projects 
                SET updated_at = CURRENT_TIMESTAMP, actual_completion_date = CURRENT_TIMESTAMP
                WHERE project_id = ?;
            """;

    // public
}