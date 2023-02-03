package com.eberry.transactionservice.service;

import com.eberry.transactionservice.entity.Customer;
import com.eberry.transactionservice.po.Transaction;
import com.eberry.transactionservice.po.TransactionResponse;
import com.eberry.transactionservice.repo.CustomerCreditRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final CustomerCreditRepo customerCreditRepo;

    public Mono<TransactionResponse> handleTransactions(Mono<String> transactions) {
        return transactions.flatMapMany(this::buildTransactions)
                .map(Transaction::validate)
                .collectMultimap(Transaction::getEmailId)
                .flatMap(this::getRejectedTransactions)
                .map(this::buildResponse);

    }

    private Flux<Transaction> buildTransactions(String transactions) {
        return Flux.fromStream(transactions.lines().map(transaction -> transaction.split(","))
                .map(this::buildTransaction));
    }

    private Transaction buildTransaction(String[] transactionDetails) {
        return Transaction.builder().withFirstName(transactionDetails[0].trim().replace("\"", "")).withLastName(transactionDetails[1].trim())
                .withEmailId(transactionDetails[2].trim()).withCost(Integer.parseInt(transactionDetails[3].trim()))
                .withTransactionNumber(transactionDetails[4].trim().replace("\"", "")).build();
    }

    private Mono<List<Transaction>> getRejectedTransactions(Map<String, Collection<Transaction>> groupedTransactions) {
        return getCreditLimitFromDB(groupedTransactions.keySet())
                .flatMap(customer -> updateLimitAndGetRejectedTransaction(customer, groupedTransactions.get(customer.getEmailId()))).collectList();
    }

    private Flux<Transaction> updateLimitAndGetRejectedTransaction(Customer customer, Collection<Transaction> transactions) {
        AtomicInteger credit = new AtomicInteger(customer.getCreditAmount());
        return Flux.fromStream(transactions.stream()).filter(book -> isCreditLimitReached(credit, book))
                .doFinally(book -> updatedCreditLimitInDB(customer, credit));
    }

    private void updatedCreditLimitInDB(Customer customer, AtomicInteger credit) {
        customerCreditRepo.save(customer.toBuilder().withCreditAmount(credit.get()).build()).subscribe();
    }

    private boolean isCreditLimitReached(AtomicInteger credit, Transaction book) {
        if (credit.get() > book.getCost()) {
            credit.addAndGet(-book.getCost());
            return false;
        } else {
            return true;
        }
    }

    private Flux<Customer> getCreditLimitFromDB(Set<String> keys) {
        return customerCreditRepo.findAllById(keys);
    }

    private TransactionResponse buildResponse(List<Transaction> rejectedTransactions) {
        return TransactionResponse.builder().withRejectedTransactions(rejectedTransactions).build();
    }
}






