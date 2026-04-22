package com.gestor.dominator.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.gestor.dominator.dto.materials.MaterialPayload;
import com.gestor.dominator.dto.materials.MaterialRecord;
import com.gestor.dominator.model.postgre.material.MaterialRq;
import com.gestor.dominator.model.postgre.material.MaterialRs;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MaterialMapper {
    @Mapping(target = "idMaterial", source = "material_id")
    MaterialPayload toMaterialPayload(MaterialRs materialRs);

    List<MaterialPayload> toMaterialResult(List<MaterialRs> materialRs);

    @Mapping(target = "slug", expression = "java(materialRecord.name()"
            + ".toLowerCase()"
            + ".trim()"
            + ".replaceAll(\"[^a-z0-9\\\\s-]\", \"\")"
            + ".replaceAll(\"\\\\s+\", \"-\")"
            + ".replaceAll(\"-+\", \"-\")"
            + ")")
    MaterialRq toRecordRq(MaterialRecord materialRecord);
}
