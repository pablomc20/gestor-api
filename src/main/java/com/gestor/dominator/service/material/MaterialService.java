package com.gestor.dominator.service.material;

import java.util.List;
import com.gestor.dominator.dto.materials.MaterialPayload;
import com.gestor.dominator.dto.materials.MaterialRecord;

public interface MaterialService {
    List<MaterialPayload> getAllMaterials();

    MaterialPayload createMaterial(MaterialRecord materialRecord);

    MaterialPayload updateMaterial(MaterialRecord materialRecord, String materialId);

    void deleteMaterial(String materialId);
}
