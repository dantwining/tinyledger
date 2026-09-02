package com.github.dantwining.tinyledger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public String getTransactions() {
        return transactionService.getTransactions().toString();
    }

    @PostMapping("/transactions")
    public void createTransactions(TransactionType type, int amount) {
        transactionService.createTransaction(type, amount);
    }
}
