package com.gestor.dominator.repository.user;

import java.util.List;

import com.gestor.dominator.dto.users.UserClientResult;
import com.gestor.dominator.model.postgre.user.CreateUserDetailsRq;
import com.gestor.dominator.model.postgre.user.CreateUserRq;
import com.gestor.dominator.model.postgre.user.CreateUserRs;
import com.gestor.dominator.model.postgre.user.GetUserByIdRq;
import com.gestor.dominator.model.postgre.user.GetUserByIdRs;

public interface UserRepository {
    GetUserByIdRs getUserDetailsById(GetUserByIdRq getUserByIdRq);

    CreateUserRs createUser(CreateUserRq createUserRq);

    String createUserDetails(CreateUserDetailsRq createUserDetailsRq);

    String updateUser(CreateUserRq createUserRq, String id);

    String updateUserDetails(CreateUserDetailsRq createUserDetailsRq);

    Integer deleteUser(String id);

    List<UserClientResult> getAllClients();

    // Auxiliar
    boolean isEnabled(String id);

    String getIdUserDetails(String idUser);
}
