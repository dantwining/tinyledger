package com.github.dantwining.tinyledger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 * Transaction
 */
public class Transaction {

    private final TransactionType type;
    private final int amount;

    @JsonCreator
    public Transaction(@JsonProperty("type") TransactionType type, @JsonProperty("amount") int amount) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type must not be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transaction amount must be greater than 0");
        }
        this.type = type;
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return amount == that.amount && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, amount);
    }

    @Override
    public String toString() {
        return type.name() + " " + amount;
    }

}
