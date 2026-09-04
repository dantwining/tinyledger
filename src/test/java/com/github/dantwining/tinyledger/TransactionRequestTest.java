package com.github.dantwining.tinyledger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionRequestTest {

    @Test
    void shouldCreateTransactionRequestWhenValid() {
        TransactionRequest request = new TransactionRequest(TransactionType.CREDIT, 100);
        assertEquals(TransactionType.CREDIT, request.type());
        assertEquals(100, request.amount());
    }

    @Test
    void shouldThrowExceptionWhenTypeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionRequest(null, 100));
    }

    @Test
    void shouldThrowExceptionWhenAmountIsZero() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionRequest(TransactionType.CREDIT, 0));
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionRequest(TransactionType.CREDIT, -10));
    }
}
