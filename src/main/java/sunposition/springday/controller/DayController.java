package sunposition.springday.controller;

import org.springframework.lang.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunposition.springday.dto.DayDto;
import sunposition.springday.mapper.DayMapper;
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


    @PostMapping("/saveSunriseSunset")
    public ResponseEntity<DayDto> saveSunriseSunset(@RequestBody DayDto dayDto) {
        Day day = DayMapper.toEntity(dayDto);
        Day savedDay = service.saveSunriseSunset(day);
        DayDto savedDayDto = DayMapper.toDto(savedDay);
        return new ResponseEntity<>(savedDayDto, HttpStatus.CREATED);
    }

    @GetMapping("/findByLocation")
    public ResponseEntity<DayDto> findByLocation(@RequestParam @Nullable String location) {
        try {
            if (location == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Day day = service.findByLocation(location);
            DayDto dayDto = DayMapper.toDto(day);
            return new ResponseEntity<>(dayDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByCoordinates")
    public ResponseEntity<DayDto> findByCoordinates(@RequestParam @Nullable String coordinates) {
        try {
            if (coordinates == null) {
                throw new IllegalArgumentException("Coordinates cannot be null");
            }
            Day day = service.findByCoordinates(coordinates);
            DayDto dayDto = DayMapper.toDto(day);
            return new ResponseEntity<>(dayDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/findByDateOfSunriseSunset")
    public ResponseEntity<DayDto> findByDateOfSunriseSunset(@RequestParam @Nullable LocalDate dateOfSunriseSunset) {
        try {
            Day day = service.findByDateOfSunriseSunset(dateOfSunriseSunset);
            DayDto dayDto = DayMapper.toDto(day);
            return new ResponseEntity<>(dayDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteByCoordinates")
    public ResponseEntity<String> deleteCityByCoordinates(@RequestParam String coordinates) {
        try {
            service.deleteDayByCoordinates(coordinates);
            return new ResponseEntity<>("The deletion was successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting city", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateSunriseSunset")
    public ResponseEntity<DayDto> updateSunriseSunset(@RequestParam String location, @RequestParam String coordinates, @RequestParam LocalDate dateOfSunriseSunset) {
        try {
            Day updatedDay = service.updateSunriseSunset(location, coordinates, dateOfSunriseSunset);
            DayDto updatedDayDto = DayMapper.toDto(updatedDay);
            return new ResponseEntity<>(updatedDayDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}