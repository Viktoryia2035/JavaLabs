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
    public Day findByLocation(@RequestParam String location) {
        return service.findByLocation(location);
    }

    @GetMapping("findByCoordinates")
    public Day findByCoordinates(@RequestParam String coordinates) {
        return service.findByCoordinates(coordinates);
    }

    @GetMapping("findByDateOfSunriseSunset")
    public Day findByDateOfSunriseSunset(@RequestParam LocalDate dateOfSunriseSunset) {
        return service.findByDateOfSunriseSunset(dateOfSunriseSunset);
    }

    @DeleteMapping("deleteSunriseSunset")
    public ResponseEntity<String> deleteCitySunriseSunset(@RequestParam String location) {
        try {
            String result = service.deleteDaySunriseSunset(location);
            return new ResponseEntity<>("The deletion was successful", HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>("Sunrise/sunset not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteByCoordinates")
    public ResponseEntity<String> deleteCityByCoordinates(@RequestParam String coordinates) {
        try {
            String result = service.deleteDayByCoordinates(coordinates);
            return new ResponseEntity<>("The deletion was successful", HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>("Sunrise/sunset not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateSunriseSunset")
    public Day updateSunriseSunset(@RequestParam String location, @RequestParam String coordinates) {
        return service.updateSunriseSunset(location, coordinates);
    }
}