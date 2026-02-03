package com.gestor.dominator.repository.project;

public final class ProjectQueryBD {

    private ProjectQueryBD() {
    }

    public static String GET_PROJECT_DETAILS_BY_ID = """
            SELECT p.user_client, p.user_employee, p.title, p.size, p.style, p.additionals, ca.name as category, p.status,
                p.start_date, p.estimated_completion_date as end_date,
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
                SELECT fn_create_new_project(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

}
