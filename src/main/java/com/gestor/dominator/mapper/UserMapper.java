package com.gestor.dominator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gestor.dominator.dto.users.UserDetailsRecord;
import com.gestor.dominator.dto.users.UserDetailsResult;
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

}
