package sunposition.springday.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunposition.springday.exception.SunriseSunsetException;
import sunposition.springday.model.Country;
import sunposition.springday.service.CountryService;

import java.util.List;

import static sunposition.springday.service.CountryService.MESSAGE_COUNTRY_ALREADY_EXISTS;
import static sunposition.springday.service.CountryService.MESSAGE_OF_COUNTRY;

@RestController
@RequestMapping("/api/v2/country")
@AllArgsConstructor
public class CountryController {

    private final CountryService service;

    @GetMapping()
    public List<Country> findAllCountry() {
        return service.findAll();
    }

    @PostMapping("saveCountry")
    public ResponseEntity<Object> saveCountry(@RequestBody Country country) {
        try {
            Country savedCountry = service.saveCountry(country);
            return new ResponseEntity<>(savedCountry, HttpStatus.CREATED);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(MESSAGE_COUNTRY_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("findName")
    public ResponseEntity<Object> findByNameCountry(@RequestParam String name) {
        try {
            Country country = service.findByNameCountry(name);
            return new ResponseEntity<>(country, HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(MESSAGE_OF_COUNTRY, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteByName")
    public ResponseEntity<String> deleteCountryByName(@RequestParam String name) {
        try {
            service.deleteCountryByName(name);
            return new ResponseEntity<>("The deletion was successful", HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>(MESSAGE_OF_COUNTRY, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("updateByName")
    public ResponseEntity<String> updateCountryByName(@RequestParam String name, @RequestParam String newName) {
        try {
            Country updatedCountry = service.updateCountryByName(name, newName);
            return new ResponseEntity<>("Updated city: " + updatedCountry.getName(), HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            if (e.getMessage().equals(CountryService.MESSAGE_COUNTRY_ALREADY_EXISTS)) {
                return new ResponseEntity<>(MESSAGE_COUNTRY_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
            } else if (e.getMessage().equals(CountryService.MESSAGE_OF_COUNTRY)) {
                return new ResponseEntity<>(MESSAGE_OF_COUNTRY, HttpStatus.NOT_FOUND);
            }
        }
        return null;
    }
}