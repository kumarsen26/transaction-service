package com.eberry.transactionservice.po;

public enum ErrorCodes {
    CUSTOMER_NOT_FOUND(103, "Customer not found"), INVALID_REQUEST(106, "Invalid Request"),
    INTERNAL_ERROR(99, "Sorry Something is Wrong");

    private final int code;
    private final String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
