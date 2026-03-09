package com.gestor.dominator.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.business.MaterialBusiness;
import com.gestor.dominator.dto.materials.MaterialPayload;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class MaterialsController {

    private final MaterialBusiness materialBusiness;

    @GetMapping("/all")
    public ResponseEntity<List<MaterialPayload>> getAllMaterials() {
        return ResponseEntity.ok(materialBusiness.getAllMaterials());
    }
}
