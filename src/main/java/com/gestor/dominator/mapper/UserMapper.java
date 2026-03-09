package com.gestor.dominator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gestor.dominator.dto.projects.UserPayload;
import com.gestor.dominator.dto.users.UserDetailsRecord;
import com.gestor.dominator.dto.users.UserDetailsResult;
import com.gestor.dominator.dto.users.UserRecord;
import com.gestor.dominator.dto.users.UserResult;
import com.gestor.dominator.model.postgre.user.CreateUserRq;
import com.gestor.dominator.model.postgre.user.CreateUserRs;
import com.gestor.dominator.model.postgre.user.GetUserByIdRq;
import com.gestor.dominator.model.postgre.user.GetUserByIdRs;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", source = "userDetailsRecord.id")
    GetUserByIdRq toGetUserByIdRq(UserDetailsRecord userDetailsRecord);

    @Mapping(target = "email", source = "getUserByIdRs.email")
    @Mapping(target = "phone", source = "getUserByIdRs.phone")
    @Mapping(target = "legalRepresentative", source = "getUserByIdRs.legal_representative")
    @Mapping(target = "taxId", source = "getUserByIdRs.tax_id")
    UserDetailsResult toUserDetailsResult(GetUserByIdRs getUserByIdRs);

    @Mapping(target = "email", source = "userRecord.email")
    @Mapping(target = "phone", source = "userRecord.phone")
    @Mapping(target = "legalRepresentative", source = "userRecord.legalRepresentative")
    @Mapping(target = "taxId", source = "userRecord.taxId")
    CreateUserRq toCreateUserRq(UserRecord userRecord);

    @Mapping(target = "id", source = "createUserRs.id")
    UserResult toUserResult(CreateUserRs createUserRs);

    @Mapping(target = "id", source = "id")
    UserResult toUserResult(String id);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "email", source = "getUserByIdRs.email")
    @Mapping(target = "phone", source = "getUserByIdRs.phone")
    @Mapping(target = "legalRepresentative", source = "getUserByIdRs.legal_representative")
    UserPayload toUserPayload(GetUserByIdRs getUserByIdRs, String userId);

}
