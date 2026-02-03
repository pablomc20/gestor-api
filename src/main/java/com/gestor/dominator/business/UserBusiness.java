package com.gestor.dominator.business;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.users.UserDetailsRecord;
import com.gestor.dominator.dto.users.UserDetailsResult;
import com.gestor.dominator.mapper.UserMapper;
import com.gestor.dominator.model.postgre.user.GetUserByIdRq;
import com.gestor.dominator.model.postgre.user.GetUserByIdRs;
import com.gestor.dominator.repository.user.UserRepository;
import com.gestor.dominator.service.users.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBusiness implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetailsResult getUserDetailsById(UserDetailsRecord userDetailsRecord) {
        GetUserByIdRq getUserByIdRq = userMapper.toGetUserByIdRq(userDetailsRecord);
        GetUserByIdRs getUserByIdRs = userRepository.getUserDetailsById(getUserByIdRq);
        return userMapper.toUserDetailsResult(getUserByIdRs);
    }
}
