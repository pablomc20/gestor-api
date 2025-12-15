package com.gestor.dominator.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.interested.CreateRecord;
import com.gestor.dominator.dto.interested.CreateResult;
import com.gestor.dominator.service.interested.InteresatedService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/interested")
@RequiredArgsConstructor
public class InterestedController {

    private final InteresatedService interesatedService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateResult createInteresated(@RequestBody CreateRecord record) {
        return interesatedService.createInteresated(record);
    }

}
