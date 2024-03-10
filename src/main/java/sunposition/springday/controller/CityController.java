package sunposition.springday.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sunposition.springday.exception.SunriseSunsetException;
import sunposition.springday.model.City;
import sunposition.springday.service.CityService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/city")
@AllArgsConstructor
public class CityController {

    private final CityService service;

    @GetMapping()
    public List<City> findAllCity() {
        return service.findAll();
    }

    @PostMapping("/saveCity")
    public City saveCity(@RequestBody City city) {
        return service.saveCity(city);
    }

    @GetMapping("findName")
    public City findByNameCity(@RequestParam String name) {
        return service.findByNameCity(name);
    }

    @DeleteMapping("deleteByName")
    public ResponseEntity<String> deleteCityByName(@RequestParam String name) {
        try {
            service.deleteCityByName(name);
            return new ResponseEntity<>("The deletion was successful", HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("updateByName")
    public ResponseEntity<String> updateCityByName(@RequestParam String name, @RequestParam String newName) {
        try {
            City updatedCity = service.updateCityByName(name, newName);
            return new ResponseEntity<>("Updated city: " + updatedCity.getName(), HttpStatus.OK);
        } catch (SunriseSunsetException e) {
            return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND);
        }
    }
}