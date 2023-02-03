package com.eberry.transactionservice.utils;

import com.eberry.transactionservice.entity.Customer;
import com.eberry.transactionservice.po.UpdateCreditRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RequestResponseUtils {

    static Mono<Customer> buildCustomer(){
       return Mono.just(Customer.builder().withCreditAmount(100).withEmailId("john@doe.com").withFirstName("john").withLastName("doe").build());
    }

    static Mono<Customer> buildCustomerToSave() {
        return Mono.just(Customer.builder().withCreditAmount(110).withEmailId("john@doe.com").withFirstName("john").withLastName("doe").build());
    }

    static Mono<UpdateCreditRequest> buildCreditRequest() {
        return Mono.just(UpdateCreditRequest.builder().withEmail("john@doe.com").withCreditAmount(10).build());
    }

    static Mono<String> buildTransactions() {
        String transactions = TestUtils.readFileToString("data/transactions.txt");
        return Mono.just(transactions);
    }

    static Flux<Customer> buildCustomers() {
        return Flux.just(Customer.builder().withCreditAmount(600).withEmailId("john@doe.com").withFirstName("John").withLastName("Doe").build(),
                Customer.builder().withCreditAmount(100).withEmailId("john@doe1.com").withFirstName("John").withLastName("Doe1").build(),
                Customer.builder().withCreditAmount(300).withEmailId("john@doe2.com").withFirstName("John").withLastName("Doe2").build());
    }

    static Flux<Customer> buildCustomersWithPartialCredits() {
        return Flux.just(Customer.builder().withCreditAmount(500).withEmailId("john@doe.com").withFirstName("John").withLastName("Doe").build(),
                Customer.builder().withCreditAmount(10).withEmailId("john@doe1.com").withFirstName("John").withLastName("Doe1").build(),
                Customer.builder().withCreditAmount(300).withEmailId("john@doe2.com").withFirstName("John").withLastName("Doe2").build());
    }

    static Flux<Customer> buildCustomersWithCompleteCredits() {
        return Flux.just(Customer.builder().withCreditAmount(500).withEmailId("john@doe.com").withFirstName("John").withLastName("Doe").build(),
                Customer.builder().withCreditAmount(3).withEmailId("john@doe1.com").withFirstName("John").withLastName("Doe1").build(),
                Customer.builder().withCreditAmount(50).withEmailId("john@doe2.com").withFirstName("John").withLastName("Doe2").build());
    }

}
