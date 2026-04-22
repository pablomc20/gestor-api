package com.gestor.dominator.repository.material;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.exceptions.custom.DataValidationException;
import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.material.MaterialRq;
import com.gestor.dominator.model.postgre.material.MaterialRs;

import static com.gestor.dominator.repository.material.MaterialQueryBD.*;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MaterialRepositoryImpl implements MaterialRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<MaterialRs> materialMapper = (rs, rowNum) -> new MaterialRs(
            rs.getString("material_id"),
            rs.getString("name"),
            rs.getString("slug"));

    @Override
    public List<MaterialRs> getAllMaterials() {
        List<MaterialRs> materials = jdbcTemplate.query(GET_ALL_MATERIALS,
                materialMapper);

        if (materials.isEmpty()) {
            throw new PostgreDbException("Materials not found");
        }

        return materials;
    }

    @Override
    public MaterialRs createMaterial(MaterialRq materialRq) {
        return jdbcTemplate.queryForObject(
                CREATE_MATERIAL,
                materialMapper,
                mapCreateMaterialParams(materialRq));
    }

    @Override
    public MaterialRs updateMaterial(MaterialRq materialRq, String materialId) {
        return jdbcTemplate.queryForObject(
                UPDATE_MATERIAL,
                materialMapper,
                mapUpdateMaterialParams(materialRq, materialId));
    }

    @Override
    public Integer deleteMaterial(String materialId) {
        return jdbcTemplate.update(
                DELETE_MATERIAL,
                mapIdToUUID(materialId));
    }

    private Object[] mapCreateMaterialParams(MaterialRq materialRq) {
        return new Object[] { materialRq.name(), materialRq.slug() };
    }

    private Object[] mapUpdateMaterialParams(MaterialRq materialRq, String materialId) {
        return new Object[] { materialRq.name(), materialRq.slug(), mapIdToUUID(materialId) };
    }

    private Object mapIdToUUID(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw DataValidationException.invalidValue("material_id", id);
        }
    }

}
