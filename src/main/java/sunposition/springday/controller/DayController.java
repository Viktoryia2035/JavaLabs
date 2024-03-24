package sunposition.springday.controller;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    static final Logger LOGGER = LogManager.getLogger(DayController.class);

    @GetMapping
    public List<Day> findAllSunriseSunset() {
        LOGGER.info("Finding all sunrise and sunset times");
        List<Day> days = service.findAllSunriseSunset();
        LOGGER.info("Found {} sunrise and sunset times", days.size());
        return days;
    }

    @PostMapping("/saveSunriseSunset")
    public ResponseEntity<DayDto> saveSunriseSunset(@RequestBody final DayDto dayDto) {
        LOGGER.info("Saving sunrise and sunset time: {}", dayDto.getDate());
        Day day = DayMapper.toEntity(dayDto);
        Day savedDay = service.saveSunriseSunset(day);
        DayDto savedDayDto = DayMapper.toDto(savedDay);
        LOGGER.info("Sunrise and sunset time saved successfully: {}", savedDayDto.getDate());
        return new ResponseEntity<>(savedDayDto, HttpStatus.CREATED);
    }

    @GetMapping("/findByLocation")
    public ResponseEntity<DayDto> findByLocation(@RequestParam final String location) {
        LOGGER.info("Finding sunrise and sunset time by location: {}", location);
        try {
            Day day = service.findByLocation(location);
            if (day == null) {
                LOGGER.error("Sunrise and sunset time not found for location: {}", location);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            DayDto dayDto = DayMapper.toDto(day);
            LOGGER.info("Sunrise and sunset time found for location: {}", location);
            return new ResponseEntity<>(dayDto, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error finding sunrise and sunset time by location: {}", location, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByCoordinates")
    public ResponseEntity<DayDto> findByCoordinates(@RequestParam final String coordinates) {
        LOGGER.info("Finding sunrise and sunset time by coordinates: {}", coordinates);
        try {
            Day day = service.findByCoordinates(coordinates);
            if (day == null) {
                LOGGER.error("Sunrise and sunset time not found for coordinates: {}", coordinates);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            DayDto dayDto = DayMapper.toDto(day);
            LOGGER.info("Sunrise and sunset time found for coordinates: {}", coordinates);
            return new ResponseEntity<>(dayDto, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error finding sunrise and sunset time by coordinates: {}", coordinates, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteByCoordinates")
    public ResponseEntity<String> deleteCityByCoordinates(@RequestParam final String coordinates) {
        LOGGER.info("Deleting city by coordinates: {}", coordinates);
        try {
            service.deleteDayByCoordinates(coordinates);
            LOGGER.info("City deleted successfully by coordinates: {}", coordinates);
            return new ResponseEntity<>("The deletion was successful", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error deleting city by coordinates: {}", coordinates, e);
            return new ResponseEntity<>("Error deleting city", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateSunriseSunset")
    public ResponseEntity<DayDto> updateSunriseSunset(
            @RequestParam final String location,
            @RequestParam final String coordinates,
            @RequestParam final LocalDate dateOfSunriseSunset) {
        LOGGER.info("Updating sunrise and sunset time for location: {}, coordinates: {}, date: {}", location, coordinates, dateOfSunriseSunset);
        try {
            Day updatedDay = service.updateSunriseSunset(location, coordinates, dateOfSunriseSunset);
            if (updatedDay == null) {
                LOGGER.error("Sunrise and sunset time not updated for location: {}, coordinates: {}, date: {}", location, coordinates, dateOfSunriseSunset);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            DayDto updatedDayDto = DayMapper.toDto(updatedDay);
            LOGGER.info("Sunrise and sunset time updated successfully for location: {}, coordinates: {}, date: {}", location, coordinates, dateOfSunriseSunset);
            return new ResponseEntity<>(updatedDayDto, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Error updating sunrise and sunset time for location: {}, coordinates: {}, date: {}", location, coordinates, dateOfSunriseSunset, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
