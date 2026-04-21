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

import com.gestor.dominator.dto.chapes.ChapeRecord;
import com.gestor.dominator.dto.chapes.ChapeResult;
import com.gestor.dominator.service.chape.ChapeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chapes")
@RequiredArgsConstructor
public class ChapeController {

    private final ChapeService chapeService;

    @GetMapping("/all")
    public ResponseEntity<List<ChapeResult>> getAllChapes() {
        return ResponseEntity.ok(chapeService.getAllChapes());
    }

    @PostMapping
    public ResponseEntity<ChapeResult> createChape(@RequestBody ChapeRecord chapeRecord) {
        ChapeResult result = chapeService.createChape(chapeRecord);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/{chapeId}")
    public ResponseEntity<ChapeResult> updateChape(@PathVariable String chapeId,
            @RequestBody ChapeRecord chapeRecord) {
        return ResponseEntity.ok(chapeService.updateChape(chapeRecord, chapeId));
    }

    @DeleteMapping("/{chapeId}")
    public ResponseEntity<Void> deleteChape(@PathVariable String chapeId) {
        chapeService.deleteChape(chapeId);
        return ResponseEntity.noContent().build();
    }

}
