package sunposition.springday.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<DayDto> saveSunriseSunset(
            @RequestBody final DayDto dayDto) {
        Day day = DayMapper.toEntity(dayDto);
        Day savedDay = service.saveSunriseSunset(day);
        DayDto savedDayDto = DayMapper.toDto(savedDay);
        return new ResponseEntity<>(savedDayDto, HttpStatus.CREATED);
    }

    @GetMapping("/findByLocation")
    public ResponseEntity<DayDto> findByLocation(
            @RequestParam final String location) {
        try {
            Day day = service.findByLocation(location);
            if (day == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            DayDto dayDto = DayMapper.toDto(day);
            return new ResponseEntity<>(dayDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByCoordinates")
    public ResponseEntity<DayDto> findByCoordinates(
            @RequestParam final String coordinates) {
        try {
            Day day = service.findByCoordinates(coordinates);
            if (day == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            DayDto dayDto = DayMapper.toDto(day);
            return new ResponseEntity<>(dayDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteByCoordinates")
    public ResponseEntity<String> deleteCityByCoordinates(
            @RequestParam final String coordinates) {
        try {
            service.deleteDayByCoordinates(coordinates);
            return new ResponseEntity<>(
                    "The deletion was successful",
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Error deleting city",
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PutMapping("/updateSunriseSunset")
    public ResponseEntity<DayDto> updateSunriseSunset(
            @RequestParam final String location,
            @RequestParam final String coordinates,
            @RequestParam final LocalDate dateOfSunriseSunset) {
        try {
            Day updatedDay = service.updateSunriseSunset(
                    location,
                    coordinates,
                    dateOfSunriseSunset
            );
            if (updatedDay == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            DayDto updatedDayDto = DayMapper.toDto(updatedDay);
            return new ResponseEntity<>(updatedDayDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
