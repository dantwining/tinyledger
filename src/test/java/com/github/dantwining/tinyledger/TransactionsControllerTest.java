package com.github.dantwining.tinyledger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionsControllerTest {

    @Test
    void shouldCreateTwoTransactionsAndRetrieveThem() {
        TransactionService transactionService = new TransactionService();
        TransactionsController controller = new TransactionsController(transactionService);
        controller.createTransactions(TransactionType.CREDIT, 100);
        controller.createTransactions(TransactionType.DEBIT, 50);

        assertEquals("[CREDIT 100, DEBIT 50]", controller.getTransactions());
    }
}
