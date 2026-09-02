package com.github.dantwining.tinyledger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BalanceControllerTest {

    @Test
    void shouldReturnCurrentBalanceAsRecord() {
        TransactionService transactionService = new TransactionService();
        BalanceController controller = new BalanceController(transactionService);
        transactionService.createTransaction(TransactionType.CREDIT, 100);
        transactionService.createTransaction(TransactionType.DEBIT, 40);

        BalanceResponse response = controller.getBalance();
        assertEquals(60, response.balance());
    }
}
