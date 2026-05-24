package com.gestor.dominator.business;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.users.UserClientResult;
import com.gestor.dominator.dto.users.UserDetailsRecord;
import com.gestor.dominator.dto.users.UserDetailsResult;
import com.gestor.dominator.dto.users.UserPatchRecord;
import com.gestor.dominator.dto.users.UserPatchResult;
import com.gestor.dominator.dto.users.UserRecord;
import com.gestor.dominator.dto.users.UserResult;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.mapper.UserMapper;
import com.gestor.dominator.model.postgre.user.CreateUserDetailsRq;
import com.gestor.dominator.model.postgre.user.CreateUserRq;
import com.gestor.dominator.model.postgre.user.CreateUserRs;
import com.gestor.dominator.model.postgre.user.GetUserByIdRq;
import com.gestor.dominator.model.postgre.user.GetUserByIdRs;
import com.gestor.dominator.model.postgre.user.PatchUserDetailsRq;
import com.gestor.dominator.model.postgre.user.PatchUserRq;
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
    private final PasswordEncoder passwordEncoder;

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
                .phone(createUserRq.phone())
                .legalRepresentative(createUserRq.legalRepresentative())
                .taxId(createUserRq.taxId())
                .build();

        userRepository.createUserDetails(createUserDetailsRq);

        return userMapper.toCreateUserResult(createUserRs);
    }

    @Override
    public UserPatchResult patchUser(UserPatchRecord userRecord, String id) {
        validateUserInfo(userRecord, id);

        String passwordEncoded = passwordEncoder.encode(userRecord.password());

        PatchUserRq createUserRq = userMapper.toPatchUserRq(userRecord, passwordEncoded);
        String userId = userRepository.patchUser(createUserRq, id);

        PatchUserDetailsRq createUserDetailsRq = PatchUserDetailsRq.builder()
                .userId(userId)
                .phone(userRecord.phone())
                .legalRepresentative(userRecord.legalRepresentative())
                .build();

        userRepository.patchUserDetails(createUserDetailsRq);

        return userMapper.toPatchUserResult(userId);
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

    private void validateUserInfo(UserPatchRecord userRecord, String id) {

        Boolean isEnabled = userRepository.isEnabled(id);
        if (isEnabled == null) {
            throw new PostgreDbException("User not found");
        }

        if (isEnabled) {
            log.info("Email will not be updated");
            throw new PostgreDbException("Email will not be updated, because the user is enabled");
        }
    }

}
