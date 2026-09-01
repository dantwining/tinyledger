# tinyledger
Simple implementation of an API-driven ledger (completed as a coding exercise)

## Running the application

### Examples

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

## Build
- Maven
- Test approach
- Pipeline?
