package sunposition.springday.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sunposition.springday.cache.Cache;
import sunposition.springday.dto.CountryDto;
import sunposition.springday.dto.DayDto;
import sunposition.springday.exception.SunriseSunsetException;
import sunposition.springday.mapper.CountryMapper;
import sunposition.springday.mapper.DayMapper;
import sunposition.springday.model.Country;
import sunposition.springday.model.Day;
import sunposition.springday.repository.InMemoryCountryDAO;
import sunposition.springday.repository.InMemoryDayDAO;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CountryService {
    private final InMemoryCountryDAO repositoryOfCountry;
    private final InMemoryDayDAO repositoryOfDay;
    private final Cache<String, CountryDto> countryCache;
    private final Cache<String, List<DayDto>> dayCache;

    public static final String MESSAGE_OF_COUNTRY = "Country not found";
    public static final String MESSAGE_COUNTRY_ALREADY_EXISTS =
            "Country with the same name already exists";

    public List<CountryDto> findAll() {
        List<CountryDto> cachedCountries = (List<CountryDto>) countryCache.get(
                "all"
        );
        if (cachedCountries != null) {
            return cachedCountries;
        }
        List<Country> countries = repositoryOfCountry.findAll();
        List<CountryDto> countryDtos = new ArrayList<>();
        for (Country country : countries) {
            CountryDto countryDto = CountryMapper.toDto(country);
            countryDtos.add(countryDto);
        }

        for (CountryDto countryDto : countryDtos) {
            countryCache.put(countryDto.getName(), countryDto);
        }
        return countryDtos;
    }

    public CountryDto saveCountry(final CountryDto countryDto) {
        Country country = CountryMapper.toEntity(countryDto);
        Country savedCountry = repositoryOfCountry.saveAndFlush(country);
        List<Day> days = new ArrayList<>();
        for (DayDto dayDto : countryDto.getDays()) {
            Day day = DayMapper.toEntity(dayDto);
            day.setCountry(country);
            days.add(day);
        }
        country.setDays(days);
        List<DayDto> savedDaysDto = new ArrayList<>();
        for (Day day : savedCountry.getDays()) {
            savedDaysDto.add(DayMapper.toDto(day));
        }
        countryDto.setDays(savedDaysDto);
        countryCache.put(
                savedCountry.getName(),
                CountryMapper.toDto(savedCountry)
        );
        return CountryMapper.toDto(savedCountry);
    }

    public CountryDto findByNameCountry(final String name) {
        CountryDto cachedCountry = countryCache.get(name);
        if (cachedCountry != null) {
            return cachedCountry;
        }

        Country country = repositoryOfCountry.findByName(name);
        if (country == null) {
            throw new SunriseSunsetException(MESSAGE_OF_COUNTRY);
        }

        CountryDto countryDto = CountryMapper.toDto(country);
        countryCache.put(name, countryDto);
        return countryDto;
    }

    public void deleteCountryById(final Long id) {
        Country countryToDelete = repositoryOfCountry.findById(id)
                .orElseThrow(() -> {
                    throw new SunriseSunsetException(MESSAGE_OF_COUNTRY);
                });
        if (countryToDelete != null) {
            List<Day> daysToDelete = countryToDelete.getDays();
            if (daysToDelete != null && !daysToDelete.isEmpty()) {
                repositoryOfDay.deleteAll(daysToDelete);
            }
            repositoryOfCountry.delete(countryToDelete);
            countryCache.remove(countryToDelete.getName());
        } else {
            throw new SunriseSunsetException(MESSAGE_OF_COUNTRY);
        }
    }

    public CountryDto updateCountryByName(
            final String name,
            final String newName) {
        Country existingCountry = repositoryOfCountry.findByName(name);
        if (existingCountry != null) {
            if (repositoryOfCountry.findByName(newName) != null) {
                throw new SunriseSunsetException(
                        MESSAGE_COUNTRY_ALREADY_EXISTS
                );
            }
            existingCountry.setName(newName);
            repositoryOfCountry.save(existingCountry);
            countryCache.remove(name);
            countryCache.put(newName, CountryMapper.toDto(existingCountry));
            return CountryMapper.toDto(existingCountry);
        } else {
            throw new SunriseSunsetException(MESSAGE_OF_COUNTRY);
        }
    }

    public List<DayDto> findByCountryNameAndWeatherConditions(
            final String countryName,
            final String weatherConditions) {
        String cacheKey = countryName + "_" + weatherConditions;
        List<DayDto> cachedDays = dayCache.get(cacheKey);
        if (cachedDays != null) {
            return cachedDays;
        }

        List<Day> days = repositoryOfDay.findByCountryNameAndWeatherConditions(
                countryName,
                weatherConditions
        );
        List<DayDto> dayDtos = new ArrayList<>();
        for (Day day : days) {
            dayDtos.add(DayMapper.toDto(day));
        }
        dayCache.put(cacheKey, dayDtos);
        return dayDtos;
    }
}
