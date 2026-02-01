package com.gestor.dominator.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestor.dominator.dto.contract.ContractDetailsResult;
import com.gestor.dominator.service.contract.ContractService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    // private final ContractService contractService;

    // @GetMapping("/{contractId}")
    // public ResponseEntity<ContractDetailsResult>
    // getContractDetailsById(@PathVariable UUID contractId) {
    // // return
    // ResponseEntity.ok(contractService.getContractDetailsById(contractId));
    // return ResponseEntity.ok(new ContractDetailsResult());
    // }
}
