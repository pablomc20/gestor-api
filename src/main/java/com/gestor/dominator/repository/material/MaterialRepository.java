package com.gestor.dominator.repository.material;

import java.util.List;

import com.gestor.dominator.model.postgre.material.MaterialRs;

public interface MaterialRepository {
    List<MaterialRs> getAllMaterials();
}
