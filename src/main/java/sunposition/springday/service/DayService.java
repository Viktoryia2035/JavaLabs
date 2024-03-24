package sunposition.springday.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sunposition.springday.cache.DataCache;
import sunposition.springday.dto.DayDto;
import sunposition.springday.exception.HttpErrorExceptions;
import sunposition.springday.mapper.DayMapper;
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
    private final DataCache dayCache;

    public static final String MESSAGE_OF_DAY = "Sunrise/sunset not found";
    private static final String LOCATION_PREFIX = "location_";
    private static final String COORDINATES_PREFIX = "coordinates_";

    public List<Day> findAllSunriseSunset() {
        List<Day> allDays = repository.findAll();
        for (Day day : allDays) {
            String cacheKey = LOCATION_PREFIX + day.getLocation();
            DayDto dayDto = DayMapper.toDto(day);
            dayCache.put(cacheKey, dayDto);
        }
        return allDays;
    }

    public Day saveSunriseSunset(final Day day) {
        Day savedDay = repository.save(day);
        String cacheKey = LOCATION_PREFIX + savedDay.getLocation();
        DayDto dayDto = DayMapper.toDto(savedDay);
        dayCache.put(cacheKey, dayDto);
        dayCache.clear();
        return savedDay;
    }

    public Day findByLocation(final String location) {
        String cacheKey = LOCATION_PREFIX + location;
        Object cachedObject = dayCache.get(cacheKey);
        if (cachedObject instanceof DayDto cachedDay) {
            return DayMapper.toEntity(cachedDay);
        }
        Day day = repository.findByLocation(location);
        if (day == null) {
            throw new HttpErrorExceptions.CustomNotFoundException(MESSAGE_OF_DAY);
        }
        DayDto dayDto = DayMapper.toDto(day);
        dayCache.put(cacheKey, dayDto);
        return day;
    }


    public Day findByCoordinates(final String coordinates) {
        String cacheKey = COORDINATES_PREFIX + coordinates;
        Object cachedObject = dayCache.get(cacheKey);
        if (cachedObject != null && cachedObject instanceof DayDto) {
            DayDto cachedDay = (DayDto) cachedObject;
            return DayMapper.toEntity(cachedDay);
        }
        Day day = repository.findByCoordinates(coordinates);
        if (day == null) {
            throw new HttpErrorExceptions.CustomNotFoundException(MESSAGE_OF_DAY);
        }
        DayDto dayDto = DayMapper.toDto(day);
        dayCache.put(cacheKey, dayDto);
        return day;
    }


    public void deleteDayByCoordinates(final String coordinates) {
        Day dayToDelete = repository.findByCoordinates(coordinates);
        if (dayToDelete != null) {
            repository.delete(dayToDelete);
            dayCache.remove(COORDINATES_PREFIX + coordinates);
            dayCache.clear();
        } else {
            throw new HttpErrorExceptions.CustomNotFoundException(MESSAGE_OF_DAY);
        }
    }

    public Day updateSunriseSunset(
            final String location,
            final String coordinates,
            final LocalDate dateOfSunriseSunset) {
        Day existingDay = repository.findByLocation(location);
        if (existingDay != null) {
            existingDay.setCoordinates(coordinates);
            existingDay.setDateOfSunriseSunset(dateOfSunriseSunset);
            Day updatedDay = repository.save(existingDay);
            dayCache.remove(LOCATION_PREFIX + location);
            dayCache.put(
                    COORDINATES_PREFIX + coordinates,
                    DayMapper.toDto(updatedDay)
            );
            dayCache.put(
                    "date_" + dateOfSunriseSunset.toString(),
                    DayMapper.toDto(updatedDay)
            );
            dayCache.clear();
            return updatedDay;
        } else {
            throw new HttpErrorExceptions.CustomNotFoundException(MESSAGE_OF_DAY);
        }
    }
}
