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

## OpenAPI spec
The OpenAPI spec is available at `http://localhost:8080/v3/api-docs`. For a more user-friendly experience, the Swagger UI is available at `http://localhost:8080/swagger-ui/index.html`.

## Examples
With the application running, use `curl` to create transactions, fetch the list of all transactions, and fetch the balance.

```bash
curl -X POST "http://localhost:8080/transactions" -H "Content-Type: application/json" -d '{"type":"CREDIT","amount":1000}'
curl -X POST "http://localhost:8080/transactions" -H "Content-Type: application/json" -d '{"type":"DEBIT","amount":200}'
curl -X GET "http://localhost:8080/transactions"
curl -X GET "http://localhost:8080/balance"
```

## Requirements
The following features have been implemented:
- Record money movements (deposit, withdrawal)
- View current balance
- View transaction history

The implementation is aligned with the following technical requirements:
- Features are delivered by a functional web application (no UI, just APIs)
- App runs locally
- Favour simplicity; in-memory data structures, no additional software required to run beyond libraries.

## Design
Delivering the prototype included making the following design decisions:

### Technical design decisions
- **Langauge/Framework:** The app is built in Java Spring Boot. This aligns with both the general tech stack of the broader ecosystem, and also my own previous experience. The main alternative would have been Python/Flask, which I have used for some lightwieght home projects but wouldn't fit in with the broader context of this coding challenge. 
- **API:** The API is based on HTTP/REST and JSON. This is broadly the de-facto standard for externally-facing interfaces. It can be documented nicely with OpenAPI, is easy to discuss and review, and is readily exercised using common tooling such as `curl`. There are many alternative options; gRPC, SOAP, GraphQL, Websockets... HTTP/REST with JSON best aligned with the _favour simplicity_ principle.
- **Sync vs Async API:** For systems where internal processing may cause HTTP API timeouts, and Async API (where `202 Accepted` is returned for the initial request, and a second endpoint is made available for retrieving the actual results) may be appropriate. For this system, there was no such need.
- **API spec - spec-first vs spec-last:** For simplicity, the implementation generates the API spec from the implementation. In production systems, it can be beneficial to adopt a spec-first approach, which can catch unintended changes to the API, and form the basis of a complete testing strategy (e.g. contract testing).
- **Balance Calculation - On-Demand vs Pre-Calculated:** In the initial implementation, the balance is calculated on-demand when requested. Depending on the traffic profile and/or latency requirements, it may be more appropriate to pre-calculate the balance after every transaction, so that it can be returned immediately on request.
- **Concurrency, Immutability and Idempotency:** The application makes no guarantees around concurrent access. The underlying data structures (`ArrayList`) are not concurrency-aware, and it may be possible to trigger exeptional behaviour (e.g. `ConcurrentModificationException`) under parallel execution. They also don't consider protecting the data structures against unintended (or malicious) modification, which might happen if the codebase were extended and the data strcutes themsleves (rather than their JSON counterparts on the API) were passed around the system. In terms of idempotency, the app makes no attempt to protect itself from duplicated requests, which would be recorded faithfully as repeated transactions. It is likely that any system needing such protections would need broader modifications (e.g. a durable datastore) which would impact the implementation in ways that could address these concerns.
- **Transaction History - Pagination:** The transaction history response is unbounded and unpaginated, which would be an availability/reliability risk in a production system, but is the most simple solution for this prototype.

### Functional design decisions
- **One-phase vs Two-Phase Withdrawal/Credit:** The required functionality blends _ledger_ features (`/transactions`) with _wallet_ (or _account_) features (`/balance`). Where balance capabilities are required, there is often the need to gate withdrawals so that money can only be withdrawn if balance is available. This can be done by using a two-phase withdrawal, where the first phase reserves the funds, and the second phase completes (or rolls back) the transaction. There is also a parallel (but less critical) capability that can be added to Credits, where pending credits can be registered as a first-phase action, and a sceond phase applies the credit so that it is availabhe for withdrawal. Ledgers with these features then track multiple types of balances; a Current Balance could be based only on fully-cleared transactions, whereas Available Balance could immediately reduce to reflect pending withdrawals. For this initial implementation, I have taken the simpliest possible interpretation; this legder only records transactions that have actually happened, via a one-phase API.
- **Single vs Multi-Account/Multi-User:** The service only tracks a single balance, and all transactions are assumed to act against the same account. It is likely that any real-world ledger would track multiple accounts/users (although this could be addressed at an infrastructure level, with multiple instances of TinyLedger deployed and requests routed by account to the correct instance of the service).
- **Representing Amounts:** The prototype represents amounts as a single integer value. For single-currency ledgers this might be appropriate; the single value can represent a whole number of minimum-denomination currency units (e.g. "amount in pence" for GBP). For other use cases, the ledger may need to store fractional amounts, or amounts of currency where whole minimum-denomination currency units is inappropriate. For these use cases, and alternative representation would need to be adopted.
- **Representing the Balance:** In the prototype API, the balance is returned as a signed integer. This is slightly at odds with the `\transactions` API, which has an integer and a type for Credit/Debit. The balance API could be aigned and return a "CREDIT/DEBIT" balance type alongside the amount.
- **Order is preserved:** This isn't explicitly set out in the requirements, and so is technically a design choice that I have made. It would be possible to develop an implementation where order isn't preserved, and e.g. a withdrawal that came in after a deposit is listed in the transaction history as coming before it. This wouldn't affect the calculation of the balance, but would mean that it would not be possible to infer intermediate balances from the transaction history. In general, a ledger is required to be ordered and accurate, and so I have preserved order here. 
- **Negative Balances:** The ledger doesn't protect against negative balances. If required, it is assumed that this would be a business rule whose enforcement would live outside of the ledger. 

### Build, Test and Delivery design decisions
- **Testing strategy:** The current tests cover the same functionality at the service, controller, and API layer. In a production system, there may not be value in maintaining all there types of tests across all endpoints. There are alos many other types for tests, such as contract tests, end-to-end tests, and performance tests, which may overlap. In general, an appropriate testing strategy would need to be developed and maintained.
- **Build tool:** The application is built using Maven
- **Delivery Pipeline:** The prototype has been built without any CI/CD infrastructure, with changes being pushed directly to the _main_ branch. In a production system, it is expected that a software delivery lifecycle would be defined and enforced, with main branch protection, automated and peer review cycle, and automated continuous integration and delivery.
