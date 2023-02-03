package com.eberry.transactionservice.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder(setterPrefix = "with", toBuilder = true)
public class TransactionResponse {

    @JsonProperty("Rejected Transactions")
    private List<Transaction> rejectedTransactions;
}
