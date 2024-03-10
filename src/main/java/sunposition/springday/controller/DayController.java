package sunposition.springday.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("findLocation")
    public Day findByLocation(@RequestParam String location) {
        return service.findByLocation(location);
    }

    @GetMapping("findCoordinates")
    public Day findByCoordinates(@RequestParam String coordinates) {
        return service.findByCoordinates(coordinates);
    }

    @GetMapping("findDateOfSunriseSunset")
    public Day findByDateOfSunriseSunset(@RequestParam LocalDate dateOfSunriseSunset) {
        return service.findByDateOfSunriseSunset(dateOfSunriseSunset);
    }

    @DeleteMapping("deleteSunriseSunset")
    public ResponseEntity<String> deleteCitySunriseSunset(@RequestParam String location) {
        String result = service.deleteDaySunriseSunset(location);
        if ("Delete".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Sunrise/sunset not found", HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("deleteCoordinates")
    public ResponseEntity<String> deleteCityByCoordinates(@RequestParam String coordinates) {
        String result = service.deleteDayByCoordinates(coordinates);
        if ("Delete".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Sunrise/sunset not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateByName")
    public Day updateDayByName(@RequestParam String location, @RequestParam String coordinates) {
        return service.updateDayByName(location, coordinates);
    }
}