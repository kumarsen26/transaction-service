package com.eberry.transactionservice.controller;

import com.eberry.transactionservice.repo.CustomerCreditRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static com.eberry.transactionservice.utils.TestUtils.readFileToString;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionControllerTest {
    @Autowired
    WebTestClient webClient;

    @Autowired
    CustomerCreditRepo customerCreditRepo;

    @Test
    void handleTransactionWithinCreditLimitTest() {
        String input = readFileToString("data/transaction.txt");
        webClient.post().uri("/transactions")
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(input)).exchange()
                .expectStatus().isOk()
                .expectBody().json("{\"Rejected Transactions\":[]}", true);
    }

    @Test
    void handlePartialRejectedTransactionsCreditLimitTest() {
        String input = readFileToString("data/transaction_partial.txt");
        webClient.post().uri("/transactions")
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(input)).exchange()
                .expectStatus().isOk()
                .expectBody().json(readFileToString("data/partialRejectedResponse.json"), true);
    }


    @Test
    void handleRejectedTransactionsCreditLimitTest() {
        String input = readFileToString("data/transaction_complete.txt");
        webClient.post().uri("/transactions")
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(input)).exchange()
                .expectStatus().isOk()
                .expectBody().json(readFileToString("data/rejectedResponse.json"), true);
    }

}
