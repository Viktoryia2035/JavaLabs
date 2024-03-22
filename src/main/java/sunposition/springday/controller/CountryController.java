package sunposition.springday.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunposition.springday.dto.CountryDto;
import sunposition.springday.dto.DayDto;
import sunposition.springday.exception.SunriseSunsetException;
import sunposition.springday.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/country")
@AllArgsConstructor
public class CountryController {

    private final CountryService service;

    @GetMapping
    public List<CountryDto> findAllCountry() {
        return service.findAll();
    }

    @PostMapping("/saveCountry")
    public ResponseEntity<CountryDto> saveCountry(
            @RequestBody final CountryDto countryDto) {
        try {
            CountryDto savedCountryDto = service.saveCountry(countryDto);
            return new ResponseEntity<>(savedCountryDto, HttpStatus.CREATED);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findName")
    public ResponseEntity<CountryDto> findByNameCountry(
            @RequestParam final String name) {
        try {
            CountryDto countryDto = service.findByNameCountry(name);
            return new ResponseEntity<>(countryDto, HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteCountryById(
            @RequestParam final Long id) {
        try {
            service.deleteCountryById(id);
            return new ResponseEntity<>(
                    "The deletion was successful",
                    HttpStatus.OK
            );
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/updateByName")
    public ResponseEntity<CountryDto> updateCountryByName(
            @RequestParam final String name,
            @RequestParam final String newName) {
        try {
            CountryDto updatedCountryDto = service.updateCountryByName(
                    name,
                    newName
            );
            return new ResponseEntity<>(updatedCountryDto, HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findByNameAndWeather")
    public ResponseEntity<List<DayDto>> findByCountryNameAndWeatherConditions(
            @RequestParam final String countryName,
            @RequestParam final String weatherConditions) {
        try {
            List<DayDto> dayDto = service.findByCountryNameAndWeatherConditions(
                    countryName,
                    weatherConditions
            );
            return new ResponseEntity<>(dayDto, HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
