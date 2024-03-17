package sunposition.springday.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunposition.springday.exception.SunriseSunsetException;
import sunposition.springday.model.Day;
import sunposition.springday.service.DayService;

import java.time.LocalDate;
import java.util.List;

import static sunposition.springday.service.DayService.MESSAGE_OF_DAY;

@RestController
@RequestMapping("/api/v2/sunrise_sunset")
@AllArgsConstructor
public class DayController {
    private final DayService service;

    @GetMapping
    public List<Day> findAllSunriseSunset() {
        return service.findAllSunriseSunset();
    }

    @PostMapping("saveSunriseSunset")
    public Day saveSunriseSunset(@RequestBody Day day) {
        return service.saveSunriseSunset(day);
    }

    @GetMapping("findByLocation")
    public ResponseEntity<Object> findByLocation(@RequestParam String location) {
        try {
            Day day = service.findByLocation(location);
            return new ResponseEntity<>(day, HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(MESSAGE_OF_DAY, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("findByCoordinates")
    public ResponseEntity<Object> findByCoordinates(@RequestParam String coordinates) {
        try {
            Day day = service.findByCoordinates(coordinates);
            return new ResponseEntity<>(day, HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(MESSAGE_OF_DAY, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("findByDateOfSunriseSunset")
    public ResponseEntity<Object> findByDateOfSunriseSunset(@RequestParam LocalDate dateOfSunriseSunset) {
        try {
            Day day = service.findByDateOfSunriseSunset(dateOfSunriseSunset);
            return new ResponseEntity<>(day, HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(MESSAGE_OF_DAY, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteByCoordinates")
    public ResponseEntity<String> deleteCityByCoordinates(@RequestParam String coordinates) {
        try {
            service.deleteDayByCoordinates(coordinates);
            return new ResponseEntity<>("The deletion was successful", HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(MESSAGE_OF_DAY, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateSunriseSunset")
    public Day updateSunriseSunset(@RequestParam String location, @RequestParam String coordinates, @RequestParam LocalDate dateOfSunriseSunset) {
        try {
            return service.updateSunriseSunset(location, coordinates, dateOfSunriseSunset);
        } catch (SunriseSunsetException e) {
            if (e.getMessage().equals(MESSAGE_OF_DAY) || e.getMessage().equals("Invalid coordinates or date")) {
                throw new SunriseSunsetException(e.getMessage());
            }
        }
        return null;
    }
}