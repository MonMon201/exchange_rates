# Exchange Rate and Balance

## Project Overview

This project is a web application built with Jakarta EE, utilizing servlets, JSP, and JPA for managing exchange rates and user balances. The application allows users to view exchange rates, manage their balance in different currencies, and perform currency conversions. The application demonstrates the use of container-managed transactions (CMT) to ensure data consistency and integrity.

## Features

- View current exchange rates.
- Manage user balance in different currencies.
- Add or decrease UAH balance.
- Convert currencies (only between UAH and another currency).
- Transactions rollback on failure to ensure data integrity.

## Technologies Used

- Jakarta EE (Servlets, JSP, JPA)
- Hibernate
- PostgreSQL
- Docker
- Log4j for logging
- Lombok for boilerplate code reduction

## Setup Instructions

### Prerequisites

- Java 11
- Docker
- Gradle
- PostgreSQL

### Steps to Set Up

1. **Clone the repository**

   ```sh
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Build the project**

   ```sh
   ./gradlew clean build
   ```

3. **Build the Docker image**

   ```sh
   docker build -t exchange_rate .
   ```

4. **Start the Docker containers**

   ```sh
   docker-compose up -d
   ```

5. **Access the Application**

   Open your browser and navigate to `http://localhost:8080/app`.

## Endpoints

### Exchange Rates

#### GET `/app/guest/exchangeRates`

- **Description**: View all exchange rates.
- **Response**: List of exchange rates.

#### GET `/app/admin/exchangeRates`

- **Description**: View and manage exchange rates.
- **Parameters**: Optional `currency` parameter to filter by currency.
- **Response**: List of exchange rates.

#### POST `/app/admin/exchangeRates`

- **Description**: Add a new exchange rate.
- **Parameters**: `currency`, `date`, `buyingRate`, `sellingRate`.
- **Response**: Redirects to `/admin/exchangeRates`.

#### PUT `/app/admin/exchangeRates`

- **Description**: Update an existing exchange rate.
- **Parameters**: `id`, `currency`, `date`, `buyingRate`, `sellingRate`.
- **Response**: Redirects to `/admin/exchangeRates`.

#### DELETE `/app/admin/exchangeRates`

- **Description**: Delete an exchange rate.
- **Parameters**: `id`.
- **Response**: Redirects to `/admin/exchangeRates`.

### Balance Management

#### GET `/app/balance/manage`

- **Description**: View and manage user balance.
- **Response**: List of balances.

#### POST `/app/balance/manage`

- **Description**: Perform balance operations.
- **Parameters**:
    - `action`: `addUah`, `decreaseUah`, `convert`.
    - `amount`: Amount to add, decrease, or convert.
    - `fromCurrency`, `toCurrency` (for conversion).

- **Response**: Redirects to `/balance/manage`.

## Project Structure

- **src/main/java/com/example**: Contains all Java source files.
    - **controller**: Contains servlet classes.
    - **model**: Contains entity classes.
    - **repository**: Contains repository classes for database operations.
    - **service**: Contains service classes for business logic.
- **src/main/webapp**: Contains web resources.
    - **WEB-INF**: Contains web.xml configuration file.
    - **view**: Contains JSP files for the views.
- **build.gradle**: Gradle build file.
- **docker-compose.yml**: Docker Compose configuration.
- **seed.sql**: SQL script to seed initial data.

## Logging

Logging is configured using Log4j. Configuration file is located at `src/main/resources/log4j2.xml`.

## Transaction Management

The project uses container-managed transactions (CMT) to ensure data consistency. The `@Transactional` annotation is used in service methods to demarcate transactional boundaries. In case of an exception, the transaction is rolled back to maintain data integrity.

## License

This project is licensed under the MIT License. See the LICENSE file for details.