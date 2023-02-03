package com.eberry.transactionservice.controller;

import com.eberry.transactionservice.po.CreditResponse;
import com.eberry.transactionservice.po.TransactionResponse;
import com.eberry.transactionservice.po.UpdateCreditRequest;
import com.eberry.transactionservice.service.CreditService;
import com.eberry.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final CreditService creditService;
    private final TransactionService transactionService;

    @PostMapping(value = "/credit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CreditResponse> updateCredit(@RequestBody @Valid Mono<UpdateCreditRequest> creditRequest) {
        return creditService.updateCredit(creditRequest);
    }

    @PostMapping(value = "/transactions", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionResponse> handleTransactions(@RequestBody Mono<String> transactions) {
        return transactionService.handleTransactions(transactions);
    }
}
