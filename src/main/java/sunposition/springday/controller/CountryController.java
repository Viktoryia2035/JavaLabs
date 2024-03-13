package sunposition.springday.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunposition.springday.exception.SunriseSunsetException;
import sunposition.springday.model.Country;
import sunposition.springday.service.CountryService;

import java.util.List;

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
    public Country saveCountry(@RequestBody Country country) {
        return service.saveCountry(country);
    }

    @GetMapping("findName")
    public Country findByNameCountry(@RequestParam String name) {
        return service.findByNameCountry(name);
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
            return new ResponseEntity<>(MESSAGE_OF_COUNTRY, HttpStatus.NOT_FOUND);
        }
    }
}