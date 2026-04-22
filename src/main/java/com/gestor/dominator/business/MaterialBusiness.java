package com.gestor.dominator.business;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.gestor.dominator.dto.materials.MaterialPayload;
import com.gestor.dominator.dto.materials.MaterialRecord;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.mapper.MaterialMapper;
import com.gestor.dominator.model.postgre.material.MaterialRq;
import com.gestor.dominator.repository.material.MaterialRepository;
import com.gestor.dominator.service.material.MaterialService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaterialBusiness implements MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialMapper materialMapper;

    @Cacheable("materials")
    @Override
    public List<MaterialPayload> getAllMaterials() {
        return materialMapper.toMaterialResult(materialRepository.getAllMaterials());
    }

    @CacheEvict(value = "materials", allEntries = true)
    @Override
    public MaterialPayload createMaterial(MaterialRecord materialRecord) {
        MaterialRq materialRq = materialMapper.toRecordRq(materialRecord);
        return materialMapper.toMaterialPayload(materialRepository.createMaterial(materialRq));
    }

    @CacheEvict(value = "materials", allEntries = true)
    @Override
    public MaterialPayload updateMaterial(MaterialRecord materialRecord, String materialId) {
        MaterialRq materialRq = materialMapper.toRecordRq(materialRecord);
        return materialMapper.toMaterialPayload(materialRepository.updateMaterial(materialRq, materialId));
    }

    @CacheEvict(value = "materials", allEntries = true)
    @Override
    public void deleteMaterial(String materialId) {
        Integer result = materialRepository.deleteMaterial(materialId);
        if (result == 0) {
            throw new PostgreDbException("Material not found");
        }
    }
}
