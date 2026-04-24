package com.gestor.dominator.repository.projectuser;

public final class ProjectUserQueryBD {

    private ProjectUserQueryBD() {
    }

    public static final String GET_PROJECTS_FOR_EMPLOYEE_IN_COURSE = """
            SELECT p.project_id,
                   p.title,
                   p.start_date,
                   p.estimated_completion_date,
                   p.estimated_completion_date - CURRENT_DATE AS dias_restantes,
                   ud.name AS client_name,
                   p.status,
                   ud.user_id
              FROM projects p
              JOIN users u ON u.user_id = p.user_client
              JOIN user_details ud ON ud.user_id = u.user_id
             WHERE p.user_employee = ?::uuid
               AND p.status != 'DELIVERED'
               AND p.status != 'NOT_APPLIED';
            """;

    public static final String GET_PROJECTS_FOR_EMPLOYEE_NEW = """
            SELECT p.project_id,
                   p.title,
                   p.start_date,
                   p.estimated_completion_date,
                   p.estimated_completion_date - CURRENT_DATE AS dias_restantes,
                   ud.name AS client_name,
                   p.status,
                   ud.user_id
              FROM projects p
              JOIN users u ON u.user_id = p.user_client
              JOIN user_details ud ON ud.user_id = u.user_id
             WHERE p.user_employee = ?::uuid
               AND p.status = 'NOT_APPLIED';
            """;

    public static final String GET_PROJECTS_FOR_EMPLOYEE_FINISHED = """
            SELECT p.project_id,
                   p.title,
                   p.start_date,
                   p.estimated_completion_date,
                   p.estimated_completion_date - CURRENT_DATE AS dias_restantes,
                   ud.name AS client_name,
                   p.status,
                   ud.user_id
              FROM projects p
              JOIN users u ON u.user_id = p.user_client
              JOIN user_details ud ON ud.user_id = u.user_id
             WHERE p.user_employee = ?::uuid
               AND p.status = 'DELIVERED';
            """;
}
