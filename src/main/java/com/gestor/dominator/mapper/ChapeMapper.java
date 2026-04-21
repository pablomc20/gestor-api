package com.gestor.dominator.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gestor.dominator.dto.chapes.ChapeRecord;
import com.gestor.dominator.dto.chapes.ChapeResult;
import com.gestor.dominator.model.postgre.chape.ChapeRq;
import com.gestor.dominator.model.postgre.chape.ChapeRs;

@Mapper(componentModel = "spring")
public interface ChapeMapper {

    List<ChapeResult> toGetAllChapeResult(List<ChapeRs> chapeRs);

    @Mapping(target = "idChape", source = "chapeRs.chape_id")
    @Mapping(target = "name", source = "chapeRs.name")
    ChapeResult toChapeResult(ChapeRs chapeRs);

    ChapeRq toRecordRq(ChapeRecord chapeRecord);

}
