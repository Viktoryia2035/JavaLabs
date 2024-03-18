package sunposition.springday.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunposition.springday.dto.CountryDto;
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
    public ResponseEntity<CountryDto> saveCountry(@RequestBody CountryDto countryDto) {
        try {
            CountryDto savedCountryDto = service.saveCountry(countryDto);
            return new ResponseEntity<>(savedCountryDto, HttpStatus.CREATED);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findName")
    public ResponseEntity<CountryDto> findByNameCountry(@RequestParam String name) {
        try {
            CountryDto countryDto = service.findByNameCountry(name);
            return new ResponseEntity<>(countryDto, HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteCountryById(@RequestParam Long id) {
        try {
            service.deleteCountryById(id);
            return new ResponseEntity<>("The deletion was successful", HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/updateByName")
    public ResponseEntity<CountryDto> updateCountryByName(@RequestParam String name, @RequestParam String newName) {
        try {
            CountryDto updatedCountryDto = service.updateCountryByName(name, newName);
            return new ResponseEntity<>(updatedCountryDto, HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}