package com.eberry.transactionservice.po;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder(setterPrefix = "with")
public class CreditResponse {
    private String email;
    private Integer creditAmount;
}
