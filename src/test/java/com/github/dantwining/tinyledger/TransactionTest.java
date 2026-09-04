package com.github.dantwining.tinyledger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionTest {

    @Test
    void shouldCreateTransactionWhenValid() {
        Transaction transaction = new Transaction(TransactionType.CREDIT, 100);
        assertEquals(TransactionType.CREDIT, transaction.getType());
        assertEquals(100, transaction.getAmount());
    }

    @Test
    void shouldThrowExceptionWhenTypeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Transaction(null, 100));
    }

    @Test
    void shouldThrowExceptionWhenAmountIsZero() {
        assertThrows(IllegalArgumentException.class, () -> new Transaction(TransactionType.CREDIT, 0));
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Transaction(TransactionType.CREDIT, -10));
    }
}
