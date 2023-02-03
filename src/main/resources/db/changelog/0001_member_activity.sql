--liquibase formatted sql
--changeset admin:customer_1
CREATE TABLE customer
(
    email_id      VARCHAR(100) PRIMARY KEY,
    first_name    VARCHAR(100) NOT NULL,
    last_name     VARCHAR(100) NOT NULL,
    credit_amount INT          NOT NULL
);
--rollback DROP TABLE customer
