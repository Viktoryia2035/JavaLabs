package sunposition.springday.service;

import org.springframework.web.bind.annotation.*;
import sunposition.springday.model.Day;

import java.time.LocalDate;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import sunposition.springday.repository.InMemoryDayDAO;

@Service
@AllArgsConstructor
@Primary
@Transactional
public class DayService {
    private final InMemoryDayDAO repository;

    public List<Day> findAllSunriseSunset() {
        return repository.findAll();
    }

    public Day saveSunriseSunset(Day day) {
        return repository.save(day);
    }

    public String deleteDaySunriseSunset(String location) {
        Day cityToDelete = repository.findByLocation(location);
        if (cityToDelete != null) {
            repository.delete(cityToDelete);
            return "Delete";
        } else {
            return "Not found.";
        }
    }

    public String deleteDayByCoordinates(String coordinates) {
        Day cityToDelete = repository.findByCoordinates(coordinates);
        if (cityToDelete != null) {
            repository.delete(cityToDelete);
            return "Delete";
        } else {
            return "Not found.";
        }
    }

    public Day findByLocation(String location) {
        repository.findByLocation(location);
        return null;
    }

    public Day findByCoordinates(String coordinates) {
        repository.findByCoordinates(coordinates);
        return null;
    }

    public Day findByDateOfSunriseSunset(LocalDate dateOfSunriseSunset) {
        repository.findByDateOfSunriseSunset(dateOfSunriseSunset);
        return null;
    }

    public Day updateDayByName(String location, String coordinates) {
        Day existingDay = repository.findByLocation(location);
        if (existingDay != null) {
            existingDay.setCoordinates(coordinates);
            existingDay.setLocation(location);
            repository.save(existingDay);
            return repository.save(existingDay);
        } else {
            return null;
        }
    }
}