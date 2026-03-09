package com.gestor.dominator.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.users.UserClientResult;
import com.gestor.dominator.dto.users.UserDetailsRecord;
import com.gestor.dominator.dto.users.UserDetailsResult;
import com.gestor.dominator.dto.users.UserRecord;
import com.gestor.dominator.dto.users.UserResult;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.mapper.UserMapper;
import com.gestor.dominator.model.postgre.user.CreateUserDetailsRq;
import com.gestor.dominator.model.postgre.user.CreateUserRq;
import com.gestor.dominator.model.postgre.user.CreateUserRs;
import com.gestor.dominator.model.postgre.user.GetUserByIdRq;
import com.gestor.dominator.model.postgre.user.GetUserByIdRs;
import com.gestor.dominator.repository.user.UserRepository;
import com.gestor.dominator.service.users.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserBusiness implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetailsResult getUserDetailsById(UserDetailsRecord userDetailsRecord) {
        GetUserByIdRq getUserByIdRq = userMapper.toGetUserByIdRq(userDetailsRecord);
        GetUserByIdRs getUserByIdRs = userRepository.getUserDetailsById(getUserByIdRq);
        return userMapper.toUserDetailsResult(getUserByIdRs);
    }

    @Override
    public UserResult createUser(UserRecord userRecord) {
        CreateUserRq createUserRq = userMapper.toCreateUserRq(userRecord);
        CreateUserRs createUserRs = userRepository.createUser(createUserRq);

        CreateUserDetailsRq createUserDetailsRq = CreateUserDetailsRq.builder()
                .userId(createUserRs.id())
                .name(createUserRq.legalRepresentative().split(" ")[0])
                .phone(createUserRq.phone())
                .legalRepresentative(createUserRq.legalRepresentative())
                .taxId(createUserRq.taxId())
                .build();

        userRepository.createUserDetails(createUserDetailsRq);

        return userMapper.toUserResult(createUserRs);
    }

    @Override
    public UserResult updateUser(UserRecord userRecord, String id) {
        if (userRecord.email() == null || userRecord.email().isEmpty()) {
            return userMapper.toUserResult(id);
        }

        boolean isEnabled = userRepository.isEnabled(id);
        if (isEnabled) {
            log.info("Email will not be updated");
            return userMapper.toUserResult(id);
        }

        CreateUserRq createUserRq = userMapper.toCreateUserRq(userRecord);
        String userId = userRepository.updateUser(createUserRq, id);

        String idUserDetails = userRepository.getIdUserDetails(userId);
        CreateUserDetailsRq createUserDetailsRq = CreateUserDetailsRq.builder()
                .userDetailId(idUserDetails)
                .name(createUserRq.legalRepresentative().split(" ")[0])
                .phone(createUserRq.phone())
                .legalRepresentative(createUserRq.legalRepresentative())
                .taxId(createUserRq.taxId())
                .build();

        userRepository.updateUserDetails(createUserDetailsRq);

        return userMapper.toUserResult(userId);
    }

    @Override
    public void deleteUser(String id) {
        Integer rowsAffected = userRepository.deleteUser(id);
        if (rowsAffected == 0) {
            throw new PostgreDbException("User not found");
        }
    }

    @Override
    public List<UserClientResult> getAllClients() {
        return userRepository.getAllClients();
    }

}
