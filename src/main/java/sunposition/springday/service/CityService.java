package sunposition.springday.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import sunposition.springday.exception.SunriseSunsetException;
import sunposition.springday.model.City;
import sunposition.springday.repository.InMemoryCityDAO;
import sunposition.springday.repository.InMemoryDayDAO;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
@Transactional
public class CityService {
    private final InMemoryCityDAO repositoryOfCity;
    private final InMemoryDayDAO repositoryOfDay;

    public List<City> findAll() {
        return repositoryOfCity.findAll();
    }

    public City saveCity(City newCity) {
        repositoryOfDay.saveAll(newCity.getDays());
        return repositoryOfCity.save(newCity);
    }

    public City findByNameCity(String name) {
        return repositoryOfCity.findByName(name);
    }

    public String deleteCityByName(String name) {
        try {
            City cityToDelete = repositoryOfCity.findByName(name);
            if (cityToDelete != null) {
                repositoryOfDay.deleteAll(cityToDelete.getDays());
                repositoryOfCity.delete(cityToDelete);
                return "The deletion was successful";
            } else {
                throw new SunriseSunsetException("City not found");
            }
        } catch (SunriseSunsetException e) {
            return e.getMessage();
        }
    }

    public City updateCityByName(String name, String newName) {
        City existingCity = repositoryOfCity.findByName(name);
        if (existingCity != null) {
            existingCity.setName(newName);
            repositoryOfCity.save(existingCity);
            return existingCity;
        } else {
            throw new SunriseSunsetException("City not found");
        }
    }

}