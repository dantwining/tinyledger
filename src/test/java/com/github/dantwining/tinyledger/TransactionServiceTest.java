package com.github.dantwining.tinyledger;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionServiceTest {

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService();
    }

    @Test
    void getTransactions_shouldBeEmptyInitially() {
        List<Transaction> transactions = transactionService.getTransactions();
        assertTrue(transactions.isEmpty());
    }

    @Test
    void createTransaction_shouldAddTransactionToList() {
        transactionService.createTransaction(TransactionType.CREDIT, 100);
        transactionService.createTransaction(TransactionType.DEBIT, 40);

        List<Transaction> transactions = transactionService.getTransactions();
        assertEquals(2, transactions.size());

        assertEquals(TransactionType.CREDIT, transactions.get(0).getType());
        assertEquals(100, transactions.get(0).getAmount());

        assertEquals(TransactionType.DEBIT, transactions.get(1).getType());
        assertEquals(40, transactions.get(1).getAmount());
    }

    @Test
    void createTransaction_shouldThrowExceptionWhenTypeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.createTransaction(null, 100));
    }

    @Test
    void createTransaction_shouldThrowExceptionWhenAmountIsZero() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.createTransaction(TransactionType.CREDIT, 0));
    }

    @Test
    void createTransaction_shouldThrowExceptionWhenAmountIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.createTransaction(TransactionType.DEBIT, -50));
    }

    @Test
    void getBalance_shouldBeZeroInitially() {
        assertEquals(0, transactionService.getBalance());
    }

    @Test
    void getBalance_shouldAddCredits() {
        transactionService.createTransaction(TransactionType.CREDIT, 100);
        transactionService.createTransaction(TransactionType.CREDIT, 250);

        assertEquals(350, transactionService.getBalance());
    }

    @Test
    void getBalance_shouldSubtractDebits() {
        transactionService.createTransaction(TransactionType.DEBIT, 50);
        transactionService.createTransaction(TransactionType.DEBIT, 30);

        assertEquals(-80, transactionService.getBalance());
    }

    @Test
    void getBalance_shouldCalculateNetBalanceForMixedTransactions() {
        transactionService.createTransaction(TransactionType.CREDIT, 500);
        transactionService.createTransaction(TransactionType.DEBIT, 150);
        transactionService.createTransaction(TransactionType.CREDIT, 50);
        transactionService.createTransaction(TransactionType.DEBIT, 200);

        assertEquals(200, transactionService.getBalance());
    }
}
