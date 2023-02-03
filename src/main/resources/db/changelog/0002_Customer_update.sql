--liquibase formatted sql
--changeset admin:customer_update_1

INSERT into customer(email_id, first_name, last_name, credit_amount)
values ('john@doe.com', 'John', 'Doe', 890),
       ('john@doe1.com', 'John', 'Doe1', 2000),
       ('john@doe2.com', 'John', 'Doe2', 101),
       ('john@doe3.com', 'John', 'Doe3', 300),
       ('john@doe4.com', 'John', 'Doe4', 200),
       ('john@doe5.com', 'John', 'Doe5', 400);
--rollback DELETE FROM CUSTOMER
