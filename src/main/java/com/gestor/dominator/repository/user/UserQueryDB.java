package com.gestor.dominator.repository.user;

public class UserQueryDB {

    private UserQueryDB() {
    }

    public static final String GET_USER_DETAILS_BY_ID = """
            SELECT u.email, ud.phone, ud.legal_representative, ud.tax_id
            FROM users u
            INNER JOIN user_details ud ON u.user_id = ud.user_id
            WHERE u.user_id = ?
            """;

    public static final String CREATE_USER = """
            INSERT INTO users (email, password, enabled, role)
            VALUES (?, ?, ?, ?)
            RETURNING user_id
            """;

    public static final String CREATE_USER_DETAILS = """
            INSERT INTO user_details (user_id, name, phone, legal_representative, tax_id)
            VALUES (?, ?, ?, ?, ?)
            RETURNING user_detail_id
            """;

    public static final String UPDATE_USER = """
            UPDATE users
            SET email = ?, updated_at = NOW()
            WHERE user_id = ? RETURNING user_id
            """;

    public static final String UPDATE_USER_DETAILS = """
            UPDATE user_details
            SET phone = COALESCE(?, phone),
                legal_representative = COALESCE(?, legal_representative),
                tax_id = COALESCE(?, tax_id)
            WHERE user_detail_id = ? RETURNING user_detail_id
            """;

    public static final String DELETE_USER = """
            UPDATE users
            SET enabled = false, updated_at = NOW()
            WHERE user_id = ?
            """;

    public static final String GET_ALL_CLIENTS = """
            SELECT u.user_id, ud.legal_representative
            FROM users u
            INNER JOIN user_details ud ON u.user_id = ud.user_id
            WHERE u.role = 'CLIENT'
            """;

    public static final String IS_ENABLED = """
            SELECT enabled FROM users WHERE user_id = ?
            """;

    public static final String GET_ID_USER_DETAILS = """
            SELECT user_detail_id FROM user_details WHERE user_id = ?
            """;
}
