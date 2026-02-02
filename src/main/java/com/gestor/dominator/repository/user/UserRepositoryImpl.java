package com.gestor.dominator.repository.user;

import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.user.GetUserByIdRq;
import com.gestor.dominator.model.postgre.user.GetUserByIdRs;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public GetUserByIdRs getUserDetailsById(GetUserByIdRq getUserByIdRq) {
        try {
            return jdbcTemplate.queryForObject(
                    UserQueryDB.GET_USER_DETAILS_BY_ID,
                    USER_DETAILS_MAPPER,
                    getUserByIdRq.userId());
        } catch (EmptyResultDataAccessException e) {
            throw new PostgreDbException("User not found");
        }
    }

    private static final RowMapper<GetUserByIdRs> USER_DETAILS_MAPPER = (rs, rowNum) -> new GetUserByIdRs(
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("legal_representative"),
            rs.getString("tax_id"));

}
