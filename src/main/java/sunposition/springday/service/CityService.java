package sunposition.springday.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import sunposition.springday.model.City;
import sunposition.springday.model.Day;
import sunposition.springday.repository.InMemoryCityDAO;
import sunposition.springday.repository.InMemoryDayDAO;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
@Transactional
public class CityService {
    private final InMemoryCityDAO repository;
    private final InMemoryDayDAO repos;

    public List<City>  findAll(){
        return repository.findAll();
    }


    public City saveCity(City newCity) {
        List<Day> temp = newCity.getDays();
        Day temp1;
        for(int i=0;i<temp.size();i++)
        {
            temp1=temp.get(i);
            repos.save(temp1);
        }
        return repository.save(newCity);
    }
    public City findByNameCity(String name){
        return  repository.findByName(name);
    }

    public String deleteCityByName(String name) {

        City cityToDelete = repository.findByName(name);
        List<Day> temp = cityToDelete.getDays();
        Day temp1;
        for(int i=0;i<temp.size();i++)
        {
            temp1=temp.get(i);
            repos.delete(temp1);
        }
        if (cityToDelete != null) {
            repository.delete(cityToDelete);
            return "Delete";
        } else {
            return "Not found.";
        }
    }
    public City updateCityByName(String name, String newName) {
        City existingCity = repository.findByName(name);
        if (existingCity != null) {
            existingCity.setName(newName);
            repository.save(existingCity);
            return existingCity;
        } else {
            return null;
        }
    }

}