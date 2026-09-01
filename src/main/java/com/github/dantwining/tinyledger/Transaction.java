package com.github.dantwining.tinyledger;

/**
 * Transaction
 */
public class Transaction {

    private final TransactionType type;
    private final int amount;

    public Transaction(TransactionType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return type.name() + " " + amount;
    }

}
