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
}
