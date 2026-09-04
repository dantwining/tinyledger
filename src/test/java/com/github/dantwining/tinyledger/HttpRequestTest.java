package com.github.dantwining.tinyledger;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void transactionsShouldReturnCreatedTransactionsAsJson() {
        restTestClient.post()
                .uri("http://localhost:%d/transactions".formatted(port))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new TransactionRequest(TransactionType.CREDIT, 100))
                .exchange()
                .expectStatus()
                .isOk();

        restTestClient.post()
                .uri("http://localhost:%d/transactions".formatted(port))
                .contentType(MediaType.APPLICATION_JSON)
                .body(new TransactionRequest(TransactionType.DEBIT, 50))
                .exchange()
                .expectStatus()
                .isOk();

        restTestClient.get()
                .uri("http://localhost:%d/transactions".formatted(port))
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].type").isEqualTo("CREDIT")
                .jsonPath("$[0].amount").isEqualTo(100)
                .jsonPath("$[1].type").isEqualTo("DEBIT")
                .jsonPath("$[1].amount").isEqualTo(50);

        restTestClient.get()
                .uri("http://localhost:%d/balance".formatted(port))
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.balance").isEqualTo(50);
    }

    @Test
    void postTransactionsWithQueryParamsAndNoBodyShouldBeRejected() {
        restTestClient.post()
                .uri("http://localhost:%d/transactions?type=CREDIT&amount=100".formatted(port))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void postTransactionsWithNullTypeShouldBeRejected() {
        restTestClient.post()
                .uri("http://localhost:%d/transactions".formatted(port))
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {"type": null, "amount": 100}
                        """)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void postTransactionsWithMissingTypeShouldBeRejected() {
        restTestClient.post()
                .uri("http://localhost:%d/transactions".formatted(port))
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {"amount": 100}
                        """)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void postTransactionsWithZeroAmountShouldBeRejected() {
        restTestClient.post()
                .uri("http://localhost:%d/transactions".formatted(port))
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {"type": "CREDIT", "amount": 0}
                        """)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void postTransactionsWithNegativeAmountShouldBeRejected() {
        restTestClient.post()
                .uri("http://localhost:%d/transactions".formatted(port))
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {"type": "DEBIT", "amount": -25}
                        """)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void postTransactionsWithMissingAmountShouldBeRejected() {
        restTestClient.post()
                .uri("http://localhost:%d/transactions".formatted(port))
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {"type": "CREDIT"}
                        """)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }
}
