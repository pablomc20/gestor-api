package com.gestor.dominator.repository.user;

import com.gestor.dominator.dto.users.UserClientResult;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.user.CreateUserDetailsRq;
import com.gestor.dominator.model.postgre.user.CreateUserRq;
import com.gestor.dominator.model.postgre.user.CreateUserRs;
import com.gestor.dominator.model.postgre.user.GetUserByIdRq;
import com.gestor.dominator.model.postgre.user.GetUserByIdRs;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final String TYPE_USER_CLIENT = "CLIENT";
    private final String PASSWORD_DEFAULT = "secret";
    private final boolean ENABLED_DEFAULT = false;
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

    @Override
    public CreateUserRs createUser(CreateUserRq createUserRq) {
        CreateUserRs userCreated = jdbcTemplate.queryForObject(
                UserQueryDB.CREATE_USER,
                CREATE_USER_MAPPER,
                createUserClientParams(createUserRq));

        return userCreated;
    }

    @Override
    public String createUserDetails(CreateUserDetailsRq createUserDetailsRq) {
        return jdbcTemplate.queryForObject(
                UserQueryDB.CREATE_USER_DETAILS,
                CREATE_USER_DETAILS_MAPPER,
                createUserDetailsParams(createUserDetailsRq));
    }

    @Override
    public String updateUser(CreateUserRq createUserRq, String id) {

        return jdbcTemplate.queryForObject(
                UserQueryDB.UPDATE_USER,
                UPDATE_USER_MAPPER,
                updateUserParams(createUserRq, UUID.fromString(id)));
    }

    @Override
    public String updateUserDetails(CreateUserDetailsRq createUserDetailsRq) {
        return jdbcTemplate.queryForObject(
                UserQueryDB.UPDATE_USER_DETAILS,
                UPDATE_USER_DETAILS_MAPPER,
                updateUserDetailsParams(createUserDetailsRq));
    }

    @Override
    public Integer deleteUser(String id) {
        return jdbcTemplate.update(UserQueryDB.DELETE_USER, UUID.fromString(id));
    }

    @Override
    public List<UserClientResult> getAllClients() {
        return jdbcTemplate.query(UserQueryDB.GET_ALL_CLIENTS, USER_CLIENT_MAPPER);
    }

    @Override
    public boolean isEnabled(String id) {
        return jdbcTemplate.queryForObject(
                UserQueryDB.IS_ENABLED,
                Boolean.class,
                UUID.fromString(id));
    }

    @Override
    public String getIdUserDetails(String idUser) {
        return jdbcTemplate.queryForObject(
                UserQueryDB.GET_ID_USER_DETAILS,
                String.class,
                UUID.fromString(idUser));
    }

    private static final RowMapper<GetUserByIdRs> USER_DETAILS_MAPPER = (rs, rowNum) -> new GetUserByIdRs(
            rs.getString("email"),
            rs.getString("phone"),
            rs.getString("legal_representative"),
            rs.getString("tax_id"));

    private static final RowMapper<CreateUserRs> CREATE_USER_MAPPER = (rs, rowNum) -> new CreateUserRs(
            rs.getString("user_id"));

    private static final RowMapper<String> CREATE_USER_DETAILS_MAPPER = (rs, rowNum) -> rs.getString("user_detail_id");

    private static final RowMapper<String> UPDATE_USER_MAPPER = (rs, rowNum) -> rs.getString("user_id");

    private static final RowMapper<String> UPDATE_USER_DETAILS_MAPPER = (rs, rowNum) -> rs.getString("user_detail_id");

    private static final RowMapper<UserClientResult> USER_CLIENT_MAPPER = (rs, rowNum) -> new UserClientResult(
            rs.getString("user_id"),
            rs.getString("legal_representative"));

    private Object[] createUserClientParams(CreateUserRq createUserRq) {
        return new Object[] {
                createUserRq.email(),
                PASSWORD_DEFAULT,
                ENABLED_DEFAULT,
                TYPE_USER_CLIENT };
    }

    private Object[] createUserDetailsParams(CreateUserDetailsRq createUserRq) {
        return new Object[] {
                UUID.fromString(createUserRq.userId()),
                createUserRq.name(),
                createUserRq.phone(),
                createUserRq.legalRepresentative(),
                createUserRq.taxId() };
    }

    private Object[] updateUserParams(CreateUserRq createUserRq, UUID id) {
        return new Object[] {
                createUserRq.email(),
                id };
    }

    private Object[] updateUserDetailsParams(CreateUserDetailsRq createUserDetailsRq) {
        return new Object[] {
                createUserDetailsRq.phone(),
                createUserDetailsRq.legalRepresentative(),
                createUserDetailsRq.taxId(),
                UUID.fromString(createUserDetailsRq.userId()) };
    }
}
