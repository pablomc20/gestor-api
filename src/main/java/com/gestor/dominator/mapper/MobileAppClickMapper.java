package com.gestor.dominator.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.gestor.dominator.dto.mobileappclick.MobileAppClickRecord;
import com.gestor.dominator.dto.mobileappclick.MobileAppClickResult;
import com.gestor.dominator.model.postgre.mobileappclick.MobileAppClickRq;
import com.gestor.dominator.model.postgre.mobileappclick.MobileAppClickRs;

@Mapper(componentModel = "spring")
public interface MobileAppClickMapper {

    MobileAppClickRq toRq(MobileAppClickRecord record);

    MobileAppClickResult toResult(MobileAppClickRs rs);

    List<MobileAppClickResult> toResultList(List<MobileAppClickRs> rsList);
}
