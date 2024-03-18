package sunposition.springday.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sunposition.springday.dto.CountryDto;
import sunposition.springday.dto.DayDto;
import sunposition.springday.exception.SunriseSunsetException;
import sunposition.springday.mapper.CountryMapper;
import sunposition.springday.mapper.DayMapper;
import sunposition.springday.model.Country;
import sunposition.springday.model.Day;
import sunposition.springday.repository.InMemoryCountryDAO;
import sunposition.springday.repository.InMemoryDayDAO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CountryService {
    private final InMemoryCountryDAO repositoryOfCountry;
    private final InMemoryDayDAO repositoryOfDay;

    public static final String MESSAGE_OF_COUNTRY = "Country not found";
    public static final String MESSAGE_COUNTRY_ALREADY_EXISTS = "Country with the same name already exists";

    public List<CountryDto> findAll() {
        List<Country> countries = repositoryOfCountry.findAll();
        List<CountryDto> countryDtos = new ArrayList<>();
        for (Country country : countries) {
            CountryDto countryDto = CountryMapper.toDto(country);
            countryDtos.add(countryDto);
        }
        return countryDtos;
    }

    public CountryDto saveCountry(CountryDto countryDto) {
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
        return CountryMapper.toDto(savedCountry);
    }

    public CountryDto findByNameCountry(String name) {
        Country country = repositoryOfCountry.findByName(name);
        if (country == null) {
            throw new SunriseSunsetException(MESSAGE_OF_COUNTRY);
        }
        return CountryMapper.toDto(country);
    }

    public void deleteCountryById(Long id) {
        Country countryToDelete = repositoryOfCountry.findById(id)
                .orElseThrow(() -> new SunriseSunsetException(MESSAGE_OF_COUNTRY));
        if (countryToDelete != null) {
            List<Day> daysToDelete = countryToDelete.getDays();
            if (daysToDelete != null && !daysToDelete.isEmpty()) {
                repositoryOfDay.deleteAll(daysToDelete);
            }
            repositoryOfCountry.delete(countryToDelete);
        } else {
            throw new SunriseSunsetException(MESSAGE_OF_COUNTRY);
        }
    }

    public CountryDto updateCountryByName(String name, String newName) {
        Country existingCountry = repositoryOfCountry.findByName(name);
        if (existingCountry != null) {
            if (repositoryOfCountry.findByName(newName) != null) {
                throw new SunriseSunsetException(MESSAGE_COUNTRY_ALREADY_EXISTS);
            }
            existingCountry.setName(newName);
            repositoryOfCountry.save(existingCountry);
            return CountryMapper.toDto(existingCountry);
        } else {
            throw new SunriseSunsetException(MESSAGE_OF_COUNTRY);
        }
    }

    public List<DayDto> findByCountryNameAndWeatherConditions(String countryName, String weatherConditions) {
        List<Day> days = repositoryOfDay.findByCountryNameAndWeatherConditions(countryName, weatherConditions);
        List<DayDto> dayDtos = new ArrayList<>();
        for (Day day : days) {
            dayDtos.add(DayMapper.toDto(day));
        }
        return dayDtos;
    }
}