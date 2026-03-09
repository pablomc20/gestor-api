package com.gestor.dominator.repository.material;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gestor.dominator.exceptions.custom.PostgreDbException;
import com.gestor.dominator.model.postgre.material.MaterialRs;

import static com.gestor.dominator.repository.material.MaterialQueryBD.GET_ALL_MATERIALS;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MaterialRepositoryImpl implements MaterialRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<MaterialRs> getAllMaterials() {
        List<MaterialRs> materials = jdbcTemplate.query(GET_ALL_MATERIALS,
                (rs, rowNum) -> new MaterialRs(
                        rs.getString("material_id"),
                        rs.getString("name"),
                        rs.getString("slug")));

        if (materials.isEmpty()) {
            throw new PostgreDbException("Materials not found");
        }

        return materials;
    }

}
