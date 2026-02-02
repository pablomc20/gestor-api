package com.gestor.dominator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.users.UserDetailsRecord;
import com.gestor.dominator.dto.users.UserDetailsResult;
import com.gestor.dominator.service.users.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsResult> getUserDetailsById(@PathVariable String id) {
        UserDetailsRecord userDetailsRecord = UserDetailsRecord.builder().id(id).build();
        UserDetailsResult userDetailsResult = userService.getUserDetailsById(userDetailsRecord);
        return ResponseEntity.ok(userDetailsResult);
    }
}
