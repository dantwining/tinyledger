package com.github.dantwining.tinyledger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    private final TransactionService transactionService;

    public BalanceController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/balance")
    public BalanceResponse getBalance() {
        return new BalanceResponse(transactionService.getBalance());
    }
}
