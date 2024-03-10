# Sunrise Sunset Service

***
This service takes the coordinates of a location and a date as input and returns the sunrise and sunset times for that location on the specified date.
***
## Contents
- [Task 1](#task-1)
- [Task 2](#task-2)
- [Dependencies](#dependencies)
- [Realization](#realization)
- [Usage](#usage)
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
***

## Realization
Classes are required to complete the task:  

- **DayApplication**: This class is the entry point to the Spring Boot application. It contains the main method, which launches the application.
- **SunriseSunset**: This class represents the essence of the position of the sun in your library. It contains fields such as location, coordinates, dateOfSunriseSunset, timeOfSunrise, timeOfSunset, which are used to find and output the time of sunrise and sunset. The class also contains getters and setters for accessing these fields.
- **DayController**: This class is a Spring MVC controller that handles HTTP requests related to sunrise and sunset times. It contains methods for displaying fields, searching by location (name of locality), coordinates, or a specific set date, and deleting fields. It provides interaction between the user interface and the service layer for efficient management of sunrise and sunset time data.
- **InMemoryDayDAO**: This class is a simple implementation of the field storage in the application memory. Instead of a database, the positions of the sun are stored in the twilight list.
- **DayService**: This class is a service component that provides business logic for managing sun position data. This class contains the implementation of methods for working with fields and storing them in the application memory.
- **InMemoryDayServiceImpl**: Implementation of the Day Service interface. This class contains an implementation of methods for working with fields, namely findAllSunriseSunset, saveSunriseSunset, findByLocation, findByCoordinates, findByDateOfSunriseSunset, deleteByCoordinates, deleteByDateOfSunriseSunset, deleteSunriseSunset.
***

## Usage
To use this service, follow these steps:
1. Clone the repository:
   git clone [https://github.com/your-username/sunrise-sunset-service.git](https://github.com/Viktoryia2035/JavaLabs.git)
2. Build the project:
   mvn clean install
3. Run the service:
   java -jar target/sunrise-sunset-service.jar
4. Make a GET request to the following endpoint
   - To find the time of sunrise and sunset from the list using the position (location) on the map:  
      localhost:8080/api/v2/sunrise_sunset?location=`Town`
   - To find the sunrise and sunset times from the list using the coordinates:  
      localhost:8080/api/v2/sunrise_sunset?coordinates=`xxx.yyy`
   - To find the time of sunrise and sunset from the list on a specific date:  
      localhost:8080/api/v2/sunrise_sunset?dateOfSunriseSunset=`yyyy-mm-dd`  
Replace `xxx` and `yyy` with the latitude and longitude of the location, and `yyyy-mm-dd` with the desired date.
6. The response will include the sunrise and sunset times in the format `hh:mm:ss`.
***

## SonarCloud
You can view the results of Sonar Cloud at this link: https://sonarcloud.io/project/overview?id=Viktoryia2035_JavaLabs
