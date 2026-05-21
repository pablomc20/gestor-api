package com.gestor.dominator.repository.projectstatus;

public final class ProjectStatusQueryBD {

    private ProjectStatusQueryBD() {
    }

    public static final String FIND_PROJECT_STATUS = """
            SELECT
                ps.status_log_id,
                ps.new_status,
                ps.created_at
            FROM
                project_status_log ps
            WHERE
                ps.project_id = ?::uuid
            ORDER BY
                ps.created_at DESC
            """;

    public static final String INSERT_PROJECT_STATUS = """
            INSERT INTO project_status_log (project_id, new_status, changed_by_user_id, created_at)
            VALUES (?, ?::project_status, ?, NOW())
            RETURNING status_log_id;
            """;

}
