package com.gestor.dominator.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gestor.dominator.dto.category.CategoryRecord;
import com.gestor.dominator.dto.category.CategoryResult;
import com.gestor.dominator.model.postgre.category.CategoryRq;
import com.gestor.dominator.model.postgre.category.CategoryRs;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    List<CategoryResult> toGetAllCategoryResult(List<CategoryRs> categoryRs);

    @Mapping(target = "idCategory", source = "categoryRs.category_id")
    @Mapping(target = "name", source = "categoryRs.name")
    @Mapping(target = "slug", source = "categoryRs.slug")
    CategoryResult toCategoryResult(CategoryRs categoryRs);

    CategoryRq toRecordRq(CategoryRecord categoryRecord);

}
