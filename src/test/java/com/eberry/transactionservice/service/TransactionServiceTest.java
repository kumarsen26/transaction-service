package com.eberry.transactionservice.service;

import com.eberry.transactionservice.po.TransactionResponse;
import com.eberry.transactionservice.repo.CustomerCreditRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.eberry.transactionservice.utils.RequestResponseUtils.*;
import static com.eberry.transactionservice.utils.TestUtils.readFileToString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;
    @MockBean
    CustomerCreditRepo customerCreditRepo;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService = new TransactionService(customerCreditRepo);
    }

    @Test
    void transactionsWithCreditLimitTest(){
        Mockito.when(customerCreditRepo.findAllById(Mockito.anySet())).thenReturn(buildCustomers());
        Mockito.when(customerCreditRepo.save(Mockito.any())).thenReturn(buildCustomerToSave());

        Mono<TransactionResponse> response = transactionService.handleTransactions(buildTransactions());

        StepVerifier.create(response).thenConsumeWhile(result -> {
            assertNotNull(result);
            try {
                JSONAssert.assertEquals("{\"Rejected Transactions\": []}",new ObjectMapper().writeValueAsString(result),true);
            } catch (JSONException|JsonProcessingException e) {
                e.printStackTrace();
                fail();
            }
            return true;
        }).verifyComplete();
    }

    @Test
    void partialRejectedTransactionsTest() {
        Mockito.when(customerCreditRepo.findAllById(Mockito.anySet())).thenReturn(buildCustomersWithPartialCredits());
        Mockito.when(customerCreditRepo.save(Mockito.any())).thenReturn(buildCustomerToSave());

        Mono<TransactionResponse> response = transactionService.handleTransactions(buildTransactions());

        StepVerifier.create(response).thenConsumeWhile(result -> {
            assertNotNull(result);
            try {
                JSONAssert.assertEquals(readFileToString("data/partialFailedTransactions.json"), new ObjectMapper().writeValueAsString(result), true);
            } catch (JSONException | JsonProcessingException e) {
                fail();
            }
            return true;
        }).verifyComplete();
    }

    @Test
    void fullyRejectedTransactionsTest() {
        Mockito.when(customerCreditRepo.findAllById(Mockito.anySet())).thenReturn(buildCustomersWithCompleteCredits());
        Mockito.when(customerCreditRepo.save(Mockito.any())).thenReturn(buildCustomerToSave());

        Mono<TransactionResponse> response = transactionService.handleTransactions(buildTransactions());

        StepVerifier.create(response).thenConsumeWhile(result -> {
            assertNotNull(result);
            try {
                System.out.println(new ObjectMapper().writeValueAsString(result));
                JSONAssert.assertEquals(readFileToString("data/rejectedTransactions.json"), new ObjectMapper().writeValueAsString(result), true);
            } catch (JSONException | JsonProcessingException e) {
                fail();
            }
            return true;
        }).verifyComplete();
    }


}
