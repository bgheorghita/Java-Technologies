# Java Technologies - Lab 11

- Modified the previous lab app and added support to count the added preferences for each user
- The app uses Saga (narayana-lra-coordinator must be up and running)
- Added support for Event Sourcing and CQRS
- There is the second microservice called `user-microservice` that has a table in which is stored the counter of preferences uploaded by users using the first microservice which is `l9-microservice` (used LRA for data consistency)
- There is a js client app named `js-client` which is a React App that is used as frontend for the `l9-microservice` microservice
- `l9-microservice` is secured with signed JWT
