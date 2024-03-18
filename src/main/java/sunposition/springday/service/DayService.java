package sunposition.springday.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sunposition.springday.exception.SunriseSunsetException;
import sunposition.springday.model.Day;
import sunposition.springday.repository.InMemoryDayDAO;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class DayService {
    private final InMemoryDayDAO repository;

    public static final String MESSAGE_OF_DAY = "Sunrise/sunset not found";

    public List<Day> findAllSunriseSunset() {
        return repository.findAll();
    }

    public Day saveSunriseSunset(Day day) {
        return repository.save(day);
    }

    public Day findByLocation(String location) {
        return repository.findByLocation(location);
    }

    public Day findByCoordinates(String coordinates) {
        return repository.findByCoordinates(coordinates);
    }

    public Day findByDateOfSunriseSunset(LocalDate dateOfSunriseSunset) {
        return repository.findByDateOfSunriseSunset(dateOfSunriseSunset);
    }

    public void deleteDayByCoordinates(String coordinates) {
        Day cityToDelete = repository.findByCoordinates(coordinates);
        if (cityToDelete != null) {
            repository.delete(cityToDelete);
        } else {
            throw new SunriseSunsetException(MESSAGE_OF_DAY);
        }
    }

    public Day updateSunriseSunset(String location, String coordinates, LocalDate dateOfSunriseSunset) {
        Day existingDay = repository.findByLocation(location);
        if (existingDay != null) {
            existingDay.setCoordinates(coordinates);
            existingDay.setDateOfSunriseSunset(dateOfSunriseSunset);
            return repository.save(existingDay);
        } else {
            throw new SunriseSunsetException(MESSAGE_OF_DAY);
        }
    }
}