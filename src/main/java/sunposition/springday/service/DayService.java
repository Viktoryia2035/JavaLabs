package sunposition.springday.service;

import sunposition.springday.exception.SunriseSunsetException;
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
        try {
            Day cityToDelete = repository.findByLocation(location);
            if (cityToDelete != null) {
                repository.delete(cityToDelete);
                return "The deletion was successful";
            } else {
                throw new SunriseSunsetException("Sunrise/sunset not found");
            }
        } catch (SunriseSunsetException e) {
            return e.getMessage();
        }
    }

    public String deleteDayByCoordinates(String coordinates) {
        try {
            Day cityToDelete = repository.findByCoordinates(coordinates);
            if (cityToDelete != null) {
                repository.delete(cityToDelete);
                return "The deletion was successful";
            } else {
                throw new SunriseSunsetException("Sunrise/sunset not found");
            }
        } catch (SunriseSunsetException e) {
            return e.getMessage();
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

    public Day updateSunriseSunset(String location, String coordinates) {
        Day existingDay = repository.findByLocation(location);
        if (existingDay != null) {
            existingDay.setCoordinates(coordinates);
            existingDay.setLocation(location);
            return repository.save(existingDay);
        } else {
            throw new SunriseSunsetException("Sunrise/sunset not found");
        }
    }
}