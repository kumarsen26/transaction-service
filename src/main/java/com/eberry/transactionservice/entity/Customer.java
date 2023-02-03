package com.eberry.transactionservice.entity;


import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Builder(setterPrefix = "with", toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "customer")
public class Customer {

    @Id
    @Column("email_id")
    private String emailId;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("credit_amount")
    private int creditAmount;
}
