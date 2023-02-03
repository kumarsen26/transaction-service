package com.eberry.transactionservice.exception;


import com.eberry.transactionservice.po.ErrorCodes;
import org.springframework.http.HttpStatus;

import java.io.Serial;

public class TransactionException extends Exception {

    @Serial
    private static final long serialVersionUID = 7718828512143293558L;

    private final HttpStatus httpstatus;

    public TransactionException(ErrorCodes error, HttpStatus httpstatus) {
        super(error.getMessage());
        this.httpstatus = httpstatus;
    }

    public HttpStatus getStatus() {
        return this.httpstatus;
    }
}
