package com.github.dantwining.tinyledger;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final List<Transaction> transactions = new ArrayList<>();

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void createTransaction(TransactionType type, int amount) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type must not be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than 0");
        }
        transactions.add(new Transaction(type, amount));
    }

    public int getBalance() {
        return transactions.stream()
                .mapToInt(t -> t.getType() == TransactionType.CREDIT ? t.getAmount() : -t.getAmount())
                .sum();
    }
}
