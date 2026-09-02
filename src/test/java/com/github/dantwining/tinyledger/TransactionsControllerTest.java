package com.github.dantwining.tinyledger;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionsControllerTest {

    @Test
    void shouldCreateTwoTransactionsAndRetrieveThem() {
        TransactionService transactionService = new TransactionService();
        TransactionsController controller = new TransactionsController(transactionService);
        controller.createTransaction(new TransactionRequest(TransactionType.CREDIT, 100));
        controller.createTransaction(new TransactionRequest(TransactionType.DEBIT, 50));

        List<Transaction> transactions = controller.getTransactions();
        assertEquals(2, transactions.size());
        assertEquals(TransactionType.CREDIT, transactions.get(0).getType());
        assertEquals(100, transactions.get(0).getAmount());
        assertEquals(TransactionType.DEBIT, transactions.get(1).getType());
        assertEquals(50, transactions.get(1).getAmount());
    }
}
