package com.eberry.transactionservice.exception;

import com.eberry.transactionservice.po.ErrorCodes;
import com.eberry.transactionservice.po.ErrorType;
import com.eberry.transactionservice.po.Notification;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    private static final String NOTIFICATION = "notification";

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        if (getError(request) instanceof TransactionException exception) {
            errorAttributes.put(NOTIFICATION,
                    buildNotification(exception.getMessage(), exception.getStatus()));
        } else if (getError(request) instanceof WebExchangeBindException exception) {
            errorAttributes.put(NOTIFICATION,
                    buildNotification(exception.getFieldError() != null ? exception.getFieldError().getDefaultMessage()
                            : ErrorCodes.INVALID_REQUEST.getMessage(), getStatusCode(errorAttributes))
            );
        } else if (getError(request) instanceof ConstraintViolationException exception) {
            errorAttributes.put(NOTIFICATION,
                    buildNotification(exception.getMessage(), HttpStatus.BAD_REQUEST)
            );
        } else {
            errorAttributes.put(NOTIFICATION, buildNotification(
                    (String) errorAttributes.get("message"), getStatusCode(errorAttributes)));
        }
        return errorAttributes;
    }

    private Integer getStatus(Map<String, Object> errorAttributes) {
        return (Integer) errorAttributes.get("status");
    }

    private HttpStatus getStatusCode(Map<String, Object> errorAttributes) {
        return HttpStatus.valueOf(getStatus(errorAttributes));
    }

    private Notification buildNotification(String message, HttpStatus status) {
        return Notification.builder().withCode(status.value()).withHttpStatus(status).withMessage(message)
                .withType(ErrorType.ERROR).build();
    }
}
