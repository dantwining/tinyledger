package com.github.dantwining.tinyledger;

public record TransactionRequest(TransactionType type, int amount) {
}
