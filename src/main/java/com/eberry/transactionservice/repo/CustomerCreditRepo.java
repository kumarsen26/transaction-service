package com.eberry.transactionservice.repo;

import com.eberry.transactionservice.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CustomerCreditRepo extends ReactiveCrudRepository<Customer, String> {
}
