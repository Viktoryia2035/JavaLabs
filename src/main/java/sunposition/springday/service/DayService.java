package sunposition.springday.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sunposition.springday.cache.Cache;
import sunposition.springday.dto.DayDto;
import sunposition.springday.exception.SunriseSunsetException;
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
    private final Cache<String, DayDto> dayCache;

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
        return savedDay;
    }

    public Day findByLocation(final String location) {
        String cacheKey = LOCATION_PREFIX + location;
        DayDto cachedDay = dayCache.get(cacheKey);
        if (cachedDay != null) {
            return DayMapper.toEntity(cachedDay);
        }

        Day day = repository.findByLocation(location);
        if (day == null) {
            throw new SunriseSunsetException(MESSAGE_OF_DAY);
        }

        DayDto dayDto = DayMapper.toDto(day);
        dayCache.put(cacheKey, dayDto);
        return day;
    }

    public Day findByCoordinates(final String coordinates) {
        String cacheKey = COORDINATES_PREFIX + coordinates;
        DayDto cachedDay = dayCache.get(cacheKey);
        if (cachedDay != null) {
            return DayMapper.toEntity(cachedDay);
        }

        Day day = repository.findByCoordinates(coordinates);
        if (day == null) {
            throw new SunriseSunsetException(MESSAGE_OF_DAY);
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
        } else {
            throw new SunriseSunsetException(MESSAGE_OF_DAY);
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
            return updatedDay;
        } else {
            throw new SunriseSunsetException(MESSAGE_OF_DAY);
        }
    }
}
