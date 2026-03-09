package com.gestor.dominator.service.users;

import java.util.List;

import com.gestor.dominator.dto.users.UserClientResult;
import com.gestor.dominator.dto.users.UserDetailsRecord;
import com.gestor.dominator.dto.users.UserDetailsResult;
import com.gestor.dominator.dto.users.UserRecord;
import com.gestor.dominator.dto.users.UserResult;

public interface UserService {
    UserDetailsResult getUserDetailsById(UserDetailsRecord userDetailsRecord);

    UserResult createUser(UserRecord userRecord);

    UserResult updateUser(UserRecord userRecord, String id);

    void deleteUser(String id);

    List<UserClientResult> getAllClients();
}
