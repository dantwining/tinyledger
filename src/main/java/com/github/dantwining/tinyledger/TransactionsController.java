package com.github.dantwining.tinyledger;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

    private ArrayList<Transaction> transactions = new ArrayList<>();

    @GetMapping("/transactions")
    public String getTransactions() {
        return transactions.toString();
    }

    @PostMapping("/transactions")
    public void createTransactions(TransactionType type, int amount) {
        transactions.add(new Transaction(type, amount));
    }
}
