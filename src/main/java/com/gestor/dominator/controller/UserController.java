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

import com.gestor.dominator.dto.users.UserClientResult;
import com.gestor.dominator.dto.users.UserDetailsRecord;
import com.gestor.dominator.dto.users.UserDetailsResult;
import com.gestor.dominator.dto.users.UserRecord;
import com.gestor.dominator.dto.users.UserResult;
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

    @PostMapping
    public ResponseEntity<UserResult> createUser(@RequestBody UserRecord userRecord) {
        UserResult result = userService.createUser(userRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResult> updateUser(@PathVariable String id, @RequestBody UserRecord userRecord) {
        UserResult result = userService.updateUser(userRecord, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/clients")
    public ResponseEntity<List<UserClientResult>> getAllClients() {
        List<UserClientResult> userClientResult = userService.getAllClients();
        return ResponseEntity.ok(userClientResult);
    }
}
