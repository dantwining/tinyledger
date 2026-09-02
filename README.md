# tinyledger
Simple implementation of an API-driven ledger (completed as a coding exercise)

## Building and Running the application

The application can be built using Maven. It can also be run directly from Maven using the `spring-boot:run` goal.

```
./mvnw clean install
./mvnw spring-boot:run
```

Alternatively, once built, the application can be run using the following command:

```
java -jar target/tinyledger-0.0.1-SNAPSHOT.jar
```

The application listens on port 8080 by default; this can be changed by modifying the `server.port` property in `application.properties`.


### Examples
With the application running, use `curl` to create transactions, fetch the list of all transactions, and fetch the balance.

> Note that the current API implementation uses POST without a request body; this is not recommended.

```
curl -X POST "http://localhost:8080/transactions?type=CREDIT&amount=1000"
curl -X POST "http://localhost:8080/transactions?type=DEBIT&amount=200"
curl -X GET "http://localhost:8080/transactions"
curl -X GET "http://localhost:8080/balance"
```

## Requirements
The following features should be implemented:
- Record money movements (deposit, withdrawal)
- View current balance
- View transaction history

Technical requirements:
- Features are to be delivered by a functional web application (no UI, just APIs)
- App must run locally
- Favour simplicity; in-memory data structures, no additional software required to run beyond libraries.

## Design
- Langauge/Framework: Java Spring Boot (vs Python Flask vs ...)
- API: HTTP/REST (vs gRPC vs SOAP vs GraphQL vs Websockets vs ...)
- Sync vs Async API
- One-phase vs Two-Phase withdrawal/credit
- Single vs Multi-Account (Multi-User)
- Balance Calculation - On-Demand vs Pre-Calculated (CQRS)
- Concurrency handling; dual withdrawals
- Transaction History - Pagination?
- API spec - spec-first vs generated
- POST with request body (not query parameters)
- Adopt JSON for API
- Response codes - use correct ones
- Representing Amounts - move beyond int

### Design Questions
- Should the ledger protect against negative balances? Or is this a business rule that lives outside of the ledger?
- Can/should the ledger ever reject a request? (for functional reasons)
- Should balance be an amount/type pair, rather than a signed int?
- Testing stratey - should we test at the service, controller, or API layer? Or all three?

## Build
- Maven
- Test approach
- Pipeline?
