package com.eberry.transactionservice.service;

import com.eberry.transactionservice.po.CreditResponse;
import com.eberry.transactionservice.po.ErrorCodes;
import com.eberry.transactionservice.repo.CustomerCreditRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.eberry.transactionservice.utils.RequestResponseUtils.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
class CreditServiceTest {

    @InjectMocks
    CreditService creditService;
    @MockBean
    CustomerCreditRepo customerCreditRepo;

    @BeforeAll
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        creditService = new CreditService(customerCreditRepo);
    }

    @Test
    void addCreditTest(){
        Mockito.when(customerCreditRepo.findById(Mockito.anyString())).thenReturn(buildCustomer());
        Mockito.when(customerCreditRepo.save(Mockito.any())).thenReturn(buildCustomerToSave());

        Mono<CreditResponse> creditResponse = creditService.updateCredit(buildCreditRequest());

        assertNotNull(creditResponse);
        StepVerifier.create(creditResponse).thenConsumeWhile(result -> {
            assertNotNull(result);
            Assert.isTrue(110==result.getCreditAmount(), "Credit amount is not updated");
            return true;
        }).verifyComplete();
    }

    @Test
    void addCreditForNonExistingCustomerTest(){
        Mockito.when(customerCreditRepo.findById(Mockito.anyString())).thenReturn(Mono.empty());
        Mono<CreditResponse> creditResponse = creditService.updateCredit(buildCreditRequest());
        StepVerifier.create(creditResponse).expectErrorMessage(ErrorCodes.CUSTOMER_NOT_FOUND.getMessage()).verify();
    }
}
