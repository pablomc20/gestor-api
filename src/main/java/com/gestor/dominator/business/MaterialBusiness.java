package com.gestor.dominator.business;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.materials.MaterialPayload;
import com.gestor.dominator.dto.materials.MaterialResult;
import com.gestor.dominator.mapper.MaterialMapper;
import com.gestor.dominator.repository.material.MaterialRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialBusiness {

    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;

    public List<MaterialPayload> getAllMaterials() {
        return materialMapper.toMaterialResult(materialRepository.getAllMaterials());
    }
}
