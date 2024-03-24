package sunposition.springday.controller;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunposition.springday.dto.CountryDto;
import sunposition.springday.dto.DayDto;
import sunposition.springday.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/country")
@AllArgsConstructor
public class CountryController {

    private final CountryService service;

    static final Logger LOGGER = LogManager.getLogger(CountryController.class);

    @GetMapping
    public ResponseEntity<List<CountryDto>> findAllCountry() {
        LOGGER.info("Finding all countries");
        List<CountryDto> countries = service.findAll();
        LOGGER.info("Found {} countries", countries.size());
        return ResponseEntity.ok(countries);
    }

    @PostMapping("/saveCountry")
    public ResponseEntity<CountryDto> saveCountry(
            @RequestBody final CountryDto countryDto) {
        LOGGER.info("Saving country: {}", countryDto.getName());
        CountryDto savedCountryDto = service.saveCountry(countryDto);
        LOGGER.info(
                "Country saved successfully: {}",
                savedCountryDto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCountryDto);
    }

    @GetMapping("/findName")
    public ResponseEntity<CountryDto> findByNameCountry(
            @RequestParam final String name) {
        LOGGER.info("Finding country by name");
        CountryDto countryDto = service.findByNameCountry(name);
        if (countryDto != null) {
            LOGGER.info("Country found");
        } else {
            LOGGER.info("Country not found");
        }
        return ResponseEntity.ok(countryDto);
    }


    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteCountryById(
            @RequestParam final Long id) {
        LOGGER.info("Deleting country by id: {}", id);
        service.deleteCountryById(id);
        LOGGER.info("Country deleted successfully: {}", id);
        return ResponseEntity.ok("The deletion was successful");
    }

    @PatchMapping("/updateByName")
    public ResponseEntity<CountryDto> updateCountryByName(
            @RequestParam final String name,
            @RequestParam final String newName) {
        LOGGER.info("Updating country name");
        CountryDto updatedCountryDto = service.
                updateCountryByName(name, newName);
        LOGGER.info("Country updated successfully: {}",
                updatedCountryDto.getName());
        return ResponseEntity.ok(updatedCountryDto);
    }

    @GetMapping("/findByNameAndWeather")
    public ResponseEntity<List<DayDto>> findByCountryNameAndWeatherConditions(
            @RequestParam final String countryName,
            @RequestParam final String weatherConditions) {
        LOGGER.info("Finding days by country name and weather conditions");
        List<DayDto> dayDto = service.
                findByCountryNameAndWeatherConditions(
                        countryName, weatherConditions);
        LOGGER.info("Found {} days", dayDto.size());
        return ResponseEntity.ok(dayDto);
    }
}
