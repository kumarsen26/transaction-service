package com.eberry.transactionservice.po;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder(setterPrefix = "with")
public class Notification {
    private int code;
    private String message;
    private ErrorType type;
    private HttpStatus httpStatus;

}
