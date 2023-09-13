# Klasha-API
A demo task for Klasha

# Built using Spring Boot

The application is a comprehensive web application developed using Spring Boot. The API Controller handles requests related to country data, states data, exchange data, and cities data. This README provides an overview of the system, installation instructions, usage guidelines, and API documentation.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
    - [Prerequisites Requirement](#prerequisites-requirement)
    - [Optional Requirement](#optional-requirement)
    - [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Postman Documentation](#postman-documentation)


## Features

This application comes equipped with a range of powerful features designed to make country data gathering seamless and efficient:

- **Get Country Data**: Retrieves country data based on the provided country name.
- **Get States Data**: Retrieve states and their corresponding cities' data for a specific country.
- **Get Exchange Data**: Convert currency of a source Country to a target currency.
- **Get Cities**: Retrieves Cities with the highest population from three different countries ["Ghana", "Italy", "New Zealand"].


## Technologies Used

The application leverages modern technologies to deliver a robust and efficient experience:

- **Java**: The core programming language for developing the application logic.
- **Spring Boot**: A powerful framework for building robust and scalable applications.
- **Spring Web**: Facilitates the creation of web APIs and interfaces.
- **Maven**: Manages project dependencies and provides a structured build process.
- **Git**: Version control for collaborative development.
- **Docker**: Containerization lets you build, test, and deploy applications quickly.

## Getting Started

### Prerequisites Requirement

Before getting started, ensure you have the following components installed:

1. **To successfully run the application, this environment variable will be needed. CSV_FILE_URL=[CSV_FILE_URL](https://drive.google.com/uc?id=1iy1qoQBBz7cDlSvcZu2ufnxzMID625Gs&export=download)**
2. **A docker file is on the project root directory, you would need docker installed on your local machine to run the docker file.**
3. **This project was built using JDK 17, you would need JDK 17 installed on you local machine.**

- [Java Development Kit (JDK 17)](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop/)


### Optional Requirement

1. **Docker.**
- **The first command creates the jar file.**
- **The second command builds the docker image.**
- **The third command runs the docker build.**

    ```bash
   maven install
   
   docker build -t task:latest . 
   
   docker run -d -p 6082:6082 task:latest
    ```

### Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/airnest97/klasha-api.git
   cd klasha-api
   ```

## Build and Run the Application:

Execute the following command to build and run the application:

````bash
mvn spring-boot:run
````

## Access the Application

Open your browser and navigate to `http://localhost:6082` to access the application.

## Usage

### Klasha API

- **Get Country Data**: Retrieves country data based on the provided country name.
- **Get States Data**: Retrieve states and their corresponding cities' data for a specific country.
- **Get Exchange Data**: Convert currency of a source Country to a target currency.
- **Get Cities**: Retrieves Cities with the highest population from three different countries ["Ghana", "Italy", "New Zealand"].

### API Integration

If you need to integrate the application with other applications, you can use the provided API endpoints for seamless data exchange.

## API Endpoints

The application offers the following API endpoints:

### Book Endpoints

- Country details: `GET /api/v1/klasha/country?parameter=value`
- Get state: `GET /api/v1/klasha/states?parameter=value`
- Get cities: `GET /api/v1/klasha/cities?parameter=value`
- Exchange rate: `GET /api/v1/klasha/exchange`



## postman documentation

For more detailed information about these API endpoints, refer to the API documentation.
- [Postman Documentation Collection](https://documenter.getpostman.com/view/21596187/2s9YC4Vsuw)
