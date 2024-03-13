package sunposition.springday.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import sunposition.springday.exception.SunriseSunsetException;
import sunposition.springday.model.City;
import sunposition.springday.model.Country;
import sunposition.springday.model.Day;
import sunposition.springday.repository.InMemoryCityDAO;
import sunposition.springday.repository.InMemoryCountryDAO;
import sunposition.springday.repository.InMemoryDayDAO;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
@Transactional
public class CountryService {

    private final InMemoryCityDAO repositoryOfCity;
    private final InMemoryCountryDAO repositoryOfCountry;
    private final InMemoryDayDAO repositoryOfDay;

    public static final String MESSAGE_OF_COUNTRY = "Country not found";

    public List<Country> findAll() {
        return repositoryOfCountry.findAll();
    }

    public Country saveCountry(Country newCountry) {
        List<City> cities = newCountry.getCities();
        for (City city : cities) {
            List<Day> days = city.getDays();
            repositoryOfDay.saveAll(days);
            repositoryOfCity.save(city);
        }
        return repositoryOfCountry.save(newCountry);
    }

    public Country findByNameCountry(String name) {
        return repositoryOfCountry.findByName(name);
    }

    public void deleteCountryByName(String name) {
        try {
            Country countryToDelete = repositoryOfCountry.findByName(name);
            if (countryToDelete != null) {
                List<City> cities = countryToDelete.getCities();

                for (City city : cities) {
                    List<Day> days = city.getDays();
                    repositoryOfDay.deleteAll(days);
                    repositoryOfCity.delete(city);
                }

                repositoryOfCountry.delete(countryToDelete);
            } else {
                throw new SunriseSunsetException(MESSAGE_OF_COUNTRY);
            }
        } catch (SunriseSunsetException e) {
            System.out.println(e.getMessage());
        }
    }

    public Country updateCountryByName(String name, String newName) {
        Country existingCountry = repositoryOfCountry.findByName(name);
        if (existingCountry != null) {
            existingCountry.setName(newName);
            repositoryOfCountry.save(existingCountry);
            return existingCountry;
        } else {
            throw new SunriseSunsetException(MESSAGE_OF_COUNTRY);
        }
    }
}
