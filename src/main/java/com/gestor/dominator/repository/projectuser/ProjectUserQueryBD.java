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
                   p.actual_completion_date,
                   ud.legal_representative AS client_name,
                   p.status,
                   ud.user_id,
                   ud.phone,
                   (SELECT psl.created_at
                    FROM project_status_log psl
                    WHERE project_id = p.project_id
                    ORDER BY created_at DESC
                    LIMIT 1) as last_status_date
                FROM projects p
                JOIN users u ON u.user_id = p.user_employee
                JOIN user_details ud ON ud.user_id = u.user_id
                WHERE p.user_employee = ?::uuid
                AND p.status != 'NOT_APPLIED' and p.actual_completion_date IS NULL;
            """;

    public static final String GET_PROJECTS_FOR_EMPLOYEE_NEW = """
            SELECT p.project_id,
                p.title,
                p.start_date,
                p.estimated_completion_date,
                p.estimated_completion_date - CURRENT_DATE AS dias_restantes,
                p.actual_completion_date,
                ud.legal_representative AS client_name,
                p.status,
                ud.user_id,
                ud.phone,
                (SELECT psl.created_at
                    FROM project_status_log psl
                    WHERE project_id = p.project_id
                    ORDER BY created_at DESC
                LIMIT 1) as last_status_date
            FROM projects p
            JOIN users u ON u.user_id = p.user_employee
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
                p.actual_completion_date,
                ud.legal_representative AS client_name,
                p.status,
                ud.user_id,
                ud.phone,
                (SELECT psl.created_at
                    FROM project_status_log psl
                    WHERE project_id = p.project_id
                    ORDER BY created_at DESC
                LIMIT 1) as last_status_date
            FROM projects p
            JOIN users u ON u.user_id = p.user_employee
            JOIN user_details ud ON ud.user_id = u.user_id
            WHERE p.user_employee = ?::uuid
            AND p.status = 'DELIVERED' and p.actual_completion_date IS NOT NULL;
            """;

    public static final String GET_PROJECTS_FOR_CLIENT_IN_COURSE = """
            SELECT p.project_id,
                p.title,
                p.start_date,
                p.estimated_completion_date,
                p.estimated_completion_date - CURRENT_DATE AS dias_restantes,
                p.actual_completion_date,
                ud.legal_representative AS client_name,
                p.status,
                ud.user_id,
                ud.phone,
                (SELECT psl.created_at
                    FROM project_status_log psl
                    WHERE project_id = p.project_id
                    ORDER BY created_at DESC
                LIMIT 1) as last_status_date
            FROM projects p
            JOIN users u ON u.user_id = p.user_client
            JOIN user_details ud ON ud.user_id = u.user_id
            WHERE p.user_client = ?::uuid
            AND p.status != 'NOT_APPLIED' and p.actual_completion_date IS NULL;
            """;

    public static final String GET_PROJECTS_FOR_CLIENT_NEW = """
            SELECT p.project_id,
                p.title,
                p.start_date,
                p.estimated_completion_date,
                p.estimated_completion_date - CURRENT_DATE AS dias_restantes,
                p.actual_completion_date,
                ud.legal_representative AS client_name,
                p.status,
                ud.user_id,
                ud.phone,
                (SELECT psl.created_at
                    FROM project_status_log psl
                    WHERE project_id = p.project_id
                    ORDER BY created_at DESC
                LIMIT 1) as last_status_date
            FROM projects p
            JOIN users u ON u.user_id = p.user_client
            JOIN user_details ud ON ud.user_id = u.user_id
            WHERE p.user_client = ?::uuid
            AND p.status = 'NOT_APPLIED';
            """;

    public static final String GET_PROJECTS_FOR_CLIENT_FINISHED = """
            SELECT p.project_id,
                p.title,
                p.start_date,
                p.estimated_completion_date,
                p.estimated_completion_date - CURRENT_DATE AS dias_restantes,
                p.actual_completion_date,
                ud.legal_representative AS client_name,
                p.status,
                ud.user_id,
                ud.phone,
                (SELECT psl.created_at
                    FROM project_status_log psl
                    WHERE project_id = p.project_id
                    ORDER BY created_at DESC
                LIMIT 1) as last_status_date
            FROM projects p
            JOIN users u ON u.user_id = p.user_client
            JOIN user_details ud ON ud.user_id = u.user_id
            WHERE p.user_client = ?::uuid
            AND p.status = 'DELIVERED' and p.actual_completion_date IS NOT NULL;
            """;
}
