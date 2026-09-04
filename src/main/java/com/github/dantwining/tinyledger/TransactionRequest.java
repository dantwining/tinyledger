package com.github.dantwining.tinyledger;

public record TransactionRequest(TransactionType type, int amount) {
    public TransactionRequest {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type must not be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than 0");
        }
    }
}
