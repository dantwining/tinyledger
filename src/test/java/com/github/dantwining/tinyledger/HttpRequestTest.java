package com.github.dantwining.tinyledger;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTestClient restTestClient;

    @Test
    void transactionsShouldReturnCreatedTransactions() {
        restTestClient.post()
                .uri("http://localhost:%d/transactions?type=CREDIT&amount=100".formatted(port))
                .exchange()
                .expectStatus()
                .isOk();

        restTestClient.post()
                .uri("http://localhost:%d/transactions?type=DEBIT&amount=50".formatted(port))
                .exchange()
                .expectStatus()
                .isOk();

        restTestClient.get()
                .uri("http://localhost:%d/transactions".formatted(port))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("[CREDIT 100, DEBIT 50]");
    }
}
