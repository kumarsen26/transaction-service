package com.eberry.transactionservice.service;

import com.eberry.transactionservice.entity.Customer;
import com.eberry.transactionservice.exception.TransactionException;
import com.eberry.transactionservice.po.CreditResponse;
import com.eberry.transactionservice.po.ErrorCodes;
import com.eberry.transactionservice.po.UpdateCreditRequest;
import com.eberry.transactionservice.repo.CustomerCreditRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CreditService {

    private final CustomerCreditRepo customerCreditRepo;

    public Mono<CreditResponse> updateCredit(Mono<UpdateCreditRequest> creditRequest) {
        return creditRequest.flatMap(this::buildCustomer)
                .flatMap(this::updateCreditForCustomer)
                .map(this::buildResponse);
    }

    private CreditResponse buildResponse(Customer customer) {
        return CreditResponse.builder().withEmail(customer.getEmailId()).withCreditAmount(customer.getCreditAmount()).build();
    }

    private Mono<Customer> updateCreditForCustomer(Customer updatedCustomer) {
        return customerCreditRepo.save(updatedCustomer);
    }

    private Mono<Customer> buildCustomer(UpdateCreditRequest creditRequest) {
        return customerCreditRepo.findById(creditRequest.getEmail())
                .switchIfEmpty(Mono.defer(() -> Mono
                        .error(new TransactionException(ErrorCodes.CUSTOMER_NOT_FOUND, HttpStatus.NOT_FOUND))))
                .map(customerFromDB-> customerFromDB.toBuilder().withCreditAmount(customerFromDB.getCreditAmount() + creditRequest.getCreditAmount()).build());
    }

}
