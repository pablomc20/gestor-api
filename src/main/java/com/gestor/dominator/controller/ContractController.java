package com.gestor.dominator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/contracts")
public class ContractController {

    public ContractController() {
    }

    // private final ContractService contractService;

    // @GetMapping("/{contractId}")
    // public ResponseEntity<ContractDetailsResult>
    // getContractDetailsById(@PathVariable UUID contractId) {
    // // return
    // ResponseEntity.ok(contractService.getContractDetailsById(contractId));
    // return ResponseEntity.ok(new ContractDetailsResult());
    // }
}
