package sunposition.springday.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        String result = service.deleteCityByName(name);
        if ("Delete".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("updateByName")
    public ResponseEntity<String> updateCityByName(@RequestParam String name, @RequestParam String newName) {
        City updatedRegion = service.updateCityByName(name, newName);
        if (updatedRegion != null) {
            return new ResponseEntity<>("Updated city: " , HttpStatus.OK);
        } else {
            return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND);
        }
    }

}