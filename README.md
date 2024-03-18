# Sunrise Sunset Service

***
This service takes the coordinates of a location and a date as input and returns the sunrise and sunset times for that location on the specified date.
***
## Contents
- [Task 1](#task-1)
- [Task 2](#task-2)
- [Dependencies](#dependencies)
- [HTTP requests](#HTTP-requests)
- [SonarCloud](#sonarCloud)
***

## Task 1
1. Create and run locally the simplest web/REST service using any open source example using Java stack: Spring (Spring Boot)/maven/gradle/Jersey/Spring MVC. 
2. Add a GET endpoint that accepts input parameters as query Params in the URL according to the option, and returns any hard-coded result in the form of JSON according to the option.
***

## Task 2
1. Connect a database to the project (PostgreSQL/MySQL/и т.д.).
- (0 - 7 points) - implementation of one-to-many communication;
- (8 - 10 points) - implementation of many-to-many communication.
2. Implement CRUD operations with all entities.
***

## Dependencies
This project uses the following dependencies:
- Java 21
- Maven
- PostgeSQL
***

## HTTP requests
+ GET localhost:8080/api/v2/country - show all saved contries
+ GET localhost:8080/api/v2/sunrise_sunset - show all saved days
+ POST localhost:8080/api/v2/country/saveCountry - save data
+ DELETE localhost:8080/api/v2/country/deleteById - delete data by id
+ PATCH localhost:8080/api/v2/country/updateByName - updating the country name by its current name
+ PUT localhost:8080/api/v2/sunrise_sunset/updateSunriseSunset - sunrise and sunset time updates for a given location and date
***

## SonarCloud
You can view the results of Sonar Cloud at this link: https://sonarcloud.io/project/overview?id=Viktoryia2035_JavaLabs
