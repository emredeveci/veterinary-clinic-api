# Veterinary Clinic REST API

This project is a RESTful API for managing a veterinary clinic, allowing operations on customers, animals, appointments,
doctors, vaccines, and available dates.

## Tech

- Java 22
- Spring Boot
- Hibernate
- PostgreSQL 16
- Maven
- Swagger
- Lombok

## Architecture

This RESTful API is designed using a layered architecture that separates concerns, improves maintainability, and
enhances scalability. The main layers of the architecture include:

1. **Presentation Layer (API Layer)**: Handles HTTP requests and responses, and exposes the RESTful API endpoints.
2. **Service Layer**: Contains the business logic of the application. This layer processes input from the Presentation
   Layer, applies business rules, and coordinates responses.
3. **Data Access Layer (Repository Layer)**: Manages database interactions. This layer is responsible for CRUD
   operations and querying the database.
4. **Model Layer**: Contains the domain entities that represent the data model of the application. These entities are
   used throughout the other layers.
5. **DTO Layer (Data Transfer Object Layer)**: Used to transfer data between the layers, especially between the
   Presentation Layer and Service Layer. DTOs help in decoupling the internal data structures from the API.

## Core Features

- CRUD operations for all entities: customer, animal, doctor, vaccine, appointment, available date.
- Several endpoints with various functionalities such as:
    - Filtering appointments by date and doctor, or by date and animal
    - Filtering vaccines that an animal has received
    - Filtering expiring vaccines within a specified date range

## UML Diagram

![uml_diagram](/images/uml_diagram.png)

## API Reference

| Endpoint                               | HTTP Method | Description                                                           |
|----------------------------------------|:------------|-----------------------------------------------------------------------|
| **customers**                          |             |
| `/v1/customers/{id}`                   | GET         | Retrieves the details of a customer by their ID                       |
| `/v1/customers`                        | PUT         | Updates the details of a customer                                     |
| `/v1/customers/{id}`                   | DELETE      | Removes a customer by their ID                                        |
| `/v1/customers`                        | GET         | Retrieves all customers                                               |
| `/v1/customers`                        | POST        | Adds a new customer                                                   |
| `/v1/customers/by-name`                | GET         | Retrieves a customer by their name                                    |
| `/v1/customers/{id}/animal-list`       | GET         | Retrieves all animals that belong to a customer by their id           |
|                                        |             |                                                                       |
| **animals**                            |             |                                                                       |
| `/v1/animals/{id}`                     | GET         | Retrieves the details of animal by their ID                           |
| `/v1/animals`                          | PUT         | Updates the details of an animal                                      |
| `/v1/animals/{id}`                     | DELETE      | Deletes an animal by their ID                                         |
| `/v1/animals`                          | GET         | Retrieves all animals                                                 |
| `/v1/animals`                          | POST        | Adds a new animal                                                     |
| `/v1/animals/by-name`                  | GET         | Retrieves animals by their name                                       |
| `/v1/animals/by-customer-name`         | GET         | Retrieves all animals that belong to a customer by their name         |
| `/v1/animals/by-customer-id/{id}`      | GET         | Retrieves all animals that belong to a customer by their ID           |
|                                        |             |                                                                       |                                                                                    |
| **vaccines**                           |             |                                                                       |
| `/v1/vaccines/{id}`                    | GET         | Retrieves the details of a vaccine by its ID                          |
| `/v1/vaccines`                         | PUT         | Updates a vaccine                                                     |
| `/v1/vaccines/{id}`                    | DELETE      | Deletes a vaccine by its ID                                           |
| `/v1/vaccines`                         | GET         | Retrieves all vaccines                                                |
| `/v1/vaccines`                         | POST        | Adds a new vaccine                                                    |
| `/v1/vaccines/protection-check`        | GET         | Retrieves vaccines expiring within the requested date range           |
| `/v1/vaccines/animal/{id}`             | GET         | Retrieves the vaccines received by an animal by its ID                |
|                                        |             |                                                                       |
| **doctors**                            |             |                                                                       |
| `/v1/doctors/{id}`                     | GET         | Retrieves the details of a doctor by their ID                         |
| `/v1/doctors`                          | PUT         | Updates the details of a doctor                                       |
| `/v1/doctors/{id}`                     | DELETE      | Deletes a doctor by their ID                                          |
| `/v1/doctors`                          | GET         | Retrieves all doctors                                                 |
| `/v1/doctors`                          | POST        | Adds a new doctor                                                     |
|                                        |             |                                                                       |
| **available_dates**                    |             |                                                                       |
| `/v1/availabledates/{id}`              | GET         | Retrieves the details of an available date by its ID                  |
| `/v1/availabledates`                   | PUT         | Updates the details of an available date                              |
| `/v1/availabledates/{id}`              | DELETE      | Removes an available date by its ID                                   |
| `/v1/availabledates`                   | GET         | Retrieves all available dates                                         |
| `/v1/availabledates`                   | POST        | Adds a new available date                                             |
|                                        |             |                                                                       |
| **appointments**                       |             |                                                                       |
| `/v1/appointments/{id}`                | GET         | Retrieves the details of an appointment by its ID                     |
| `/v1/appointments`                     | PUT         | Updates the details of an appointment                                 |
| `/v1/appointments/{id}`                | DELETE      | Deletes an appointment by its ID                                      |
| `/v1/appointments`                     | GET         | Retrieves all the appointments                                        |
| `/v1/appointments`                     | POST        | Adds a new appointment                                                |
| `/v1/appointments/date-doctor-availability` | GET         | Retrieves all appointments of a doctor within a specified date range  |
| `/v1/appointments/appointment-dates-animal` | GET         | Retrieves all appointments of an animal within a specified date range |

## Installation

- Clone the repository:
    - `git clone https://github.com/emredeveci/veterinary-clinic-api.git`
- Configure the database:
    - Update the `application.properties` file with your database configuration.
- Build the project:
    - `mvn clean install`
- Run the application:
    - `mvn spring-boot:run`
- You can utilize Swagger and test the API endpoints through this URL: http://localhost:8080/swagger-ui/index.html#/

## Authors

- [@emredeveci](https://github.com/emredeveci)

## License

[MIT](https://choosealicense.com/licenses/mit/)

