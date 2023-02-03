package com.eberry.transactionservice.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Set;


@Data
@Builder(setterPrefix = "with", toBuilder = true)
@ToString
public class Transaction {

    @JsonProperty("Email Id")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String emailId;

    @JsonProperty("First Name")
    @NotNull(message = "First Name is mandatory")
    private String firstName;

    @JsonProperty("Last Name")
    @NotNull(message = "Last Name is mandatory")
    private String lastName;


    @JsonIgnore
    private int cost;

    @JsonProperty("Transaction Number")
    @NotNull(message = "Transaction Number is mandatory")
    private String transactionNumber;

    public Transaction validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Transaction>> violations = validator.validate(this);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return this;
    }


}
