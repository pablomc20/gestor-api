package com.gestor.dominator.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.materials.MaterialPayload;
import com.gestor.dominator.dto.materials.MaterialRecord;
import com.gestor.dominator.service.material.MaterialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialsController {

    private final MaterialService materialService;

    @GetMapping("/all")
    public ResponseEntity<List<MaterialPayload>> getAllMaterials() {
        return ResponseEntity.ok(materialService.getAllMaterials());
    }

    @PostMapping
    public ResponseEntity<MaterialPayload> createMaterial(@RequestBody MaterialRecord materialRecord) {
        MaterialPayload result = materialService.createMaterial(materialRecord);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/{materialId}")
    public ResponseEntity<MaterialPayload> updateMaterial(@PathVariable String materialId,
            @RequestBody MaterialRecord materialRecord) {
        return ResponseEntity.ok(materialService.updateMaterial(materialRecord, materialId));
    }

    @DeleteMapping("/{materialId}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable String materialId) {
        materialService.deleteMaterial(materialId);
        return ResponseEntity.noContent().build();
    }
}
