package com.gestor.dominator.repository.user;

import com.gestor.dominator.model.postgre.user.GetUserByIdRq;
import com.gestor.dominator.model.postgre.user.GetUserByIdRs;

public interface UserRepository {
    GetUserByIdRs getUserDetailsById(GetUserByIdRq getUserByIdRq);
}
