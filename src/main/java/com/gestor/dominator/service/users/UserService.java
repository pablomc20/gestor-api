package com.gestor.dominator.service.users;

import com.gestor.dominator.dto.users.UserDetailsRecord;
import com.gestor.dominator.dto.users.UserDetailsResult;

public interface UserService {
    UserDetailsResult getUserDetailsById(UserDetailsRecord userDetailsRecord);
}
