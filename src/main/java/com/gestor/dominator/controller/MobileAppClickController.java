package com.gestor.dominator.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.mobileappclick.MobileAppClickRecord;
import com.gestor.dominator.dto.mobileappclick.MobileAppClickResult;
import com.gestor.dominator.service.mobileappclick.MobileAppClickService;


@RestController
@RequestMapping("/public/mobile-app-clicks")
public class MobileAppClickController {

    private final MobileAppClickService mobileAppClickService;

    public MobileAppClickController(MobileAppClickService mobileAppClickService) {
        this.mobileAppClickService = mobileAppClickService;
    }

    @PostMapping
    public ResponseEntity<MobileAppClickResult> createClick(@RequestBody MobileAppClickRecord record) {
        MobileAppClickResult result = mobileAppClickService.createClick(record);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @GetMapping("/{eventType}")
    public ResponseEntity<List<MobileAppClickResult>> getClicksByEventType(@PathVariable String eventType) {
        List<MobileAppClickResult> results = mobileAppClickService.getClicksByEventType(eventType);
        return ResponseEntity.ok(results);
    }
}
