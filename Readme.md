# Transaction Service


## Prerequisite
1. Maven
2. JDK 17
3. Docker
4. Shell
5. Port 8088,5432 should be available

## Build and run locally

Use the package manager [maven](https://maven.apache.org/) to build Transaction service.

```bash
mvn clean package
java -jar transaction-service-0.0.1-SNAPSHOT.jar
```

## Build and run with docker

Use the package manager [maven](https://maven.apache.org/) and [docker](https://www.docker.com) to build Transaction
Service Application

```bash
mvn clean package
docker build -t transaction-service .
docker-compose up
```

## Features

- Add/Update the credit limit for the customers who are already registered.
- Rejected transactions are returned.

## Assumptions

- Database contains already registered customer.
- The transactions will always be for the registered customers who are in DB.
- There is no requirement to persist the successful transactions.
- If there are multiple transactions for the same customer, if the first transaction failed due to not enough credit,
  the consecutive transactions may get processed if the cost of the transactions are within the creditLimit.
- The transactions will never be empty in request.
- EmailId is always unique for a customer.

## Sample Requests

### Add/Update credit
```sh
curl --location --request POST 'http://localhost:8088/credit' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "john@doe.com",
    "creditAmount": 1001
}'
```
### Get Rejected transactions
```sh
curl --location --request POST 'http://localhost:8088/transactions' \
--header 'Content-Type: text/plain' \
--data-raw '"John,Doe,john@doe.com,590,TR0001"
"John,Doe1,john@doe1.com,4,TR0002"
"John,Doe2,john@doe2.com,100,TR0002"
"John,Doe1,john@doe1.com,4,TR0002"'
```

