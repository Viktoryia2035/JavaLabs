package sunposition.springday.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sunposition.springday.cache.DataCache;
import sunposition.springday.dto.CountryDto;
import sunposition.springday.dto.DayDto;
import sunposition.springday.exception.HttpErrorExceptions;
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
    private final DataCache<String, CountryDto> countryCache;
    private final DataCache<String, List<DayDto>> dayCache;

    public static final String MESSAGE_OF_COUNTRY = "Country not found";
    public static final String MESSAGE_COUNTRY_ALREADY_EXISTS = "Country with the same name already exists";

    public List<CountryDto> findAll() {
        try {
            List<CountryDto> cachedCountries = (List<CountryDto>) countryCache.get("all");
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
        } catch (Exception e) {
            throw new HttpErrorExceptions.CustomInternalServerErrorException("An error occurred while fetching countries", e);
        }
    }

    public CountryDto saveCountry(final CountryDto countryDto) {
        try {
            if (countryDto.getName() == null || countryDto.getName().isEmpty()) {
                throw new HttpErrorExceptions.CustomBadRequestException("Country name cannot be empty");
            }
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
            countryCache.put(savedCountry.getName(), CountryMapper.toDto(savedCountry));
            countryCache.clear();
            return countryDto;
        } catch (Exception e) {
            throw new HttpErrorExceptions.CustomInternalServerErrorException("An error occurred while saving the country", e);
        }
    }

    public CountryDto findByNameCountry(final String name) {
        try {
            CountryDto cachedCountry = countryCache.get(name);
            if (cachedCountry != null) {
                return cachedCountry;
            }
            Country country = repositoryOfCountry.findByName(name);
            if (country == null) {
                throw new HttpErrorExceptions.CustomNotFoundException(MESSAGE_OF_COUNTRY);
            }
            CountryDto countryDto = CountryMapper.toDto(country);
            countryCache.put(name, countryDto);
            return countryDto;
        } catch (Exception e) {
            throw new HttpErrorExceptions.CustomInternalServerErrorException("An error occurred while fetching the country by name", e);
        }
    }

    public void deleteCountryById(final Long id) {
        try {
            Country countryToDelete = repositoryOfCountry.findById(id)
                    .orElseThrow(() -> new HttpErrorExceptions.CustomNotFoundException(MESSAGE_OF_COUNTRY));
            List<Day> daysToDelete = countryToDelete.getDays();
            if (daysToDelete != null && !daysToDelete.isEmpty()) {
                repositoryOfDay.deleteAll(daysToDelete);
            }
            repositoryOfCountry.delete(countryToDelete);
            countryCache.remove(countryToDelete.getName());
            countryCache.clear();
        } catch (Exception e) {
            throw new HttpErrorExceptions.CustomInternalServerErrorException("An error occurred while deleting the country by ID", e);
        }
    }

    public CountryDto updateCountryByName(final String name, final String newName) {
        try {
            if (newName == null || newName.isEmpty()) {
                throw new HttpErrorExceptions.CustomBadRequestException("New country name cannot be empty");
            }
            Country existingCountry = repositoryOfCountry.findByName(name);
            if (existingCountry == null) {
                throw new HttpErrorExceptions.CustomNotFoundException(MESSAGE_OF_COUNTRY);
            }
            if (repositoryOfCountry.findByName(newName) != null) {
                throw new HttpErrorExceptions.CustomBadRequestException(MESSAGE_COUNTRY_ALREADY_EXISTS);
            }
            existingCountry.setName(newName);
            repositoryOfCountry.save(existingCountry);
            countryCache.remove(name);
            countryCache.put(newName, CountryMapper.toDto(existingCountry));
            countryCache.remove(newName);
            countryCache.clear();
            return CountryMapper.toDto(existingCountry);
        } catch (Exception e) {
            throw new HttpErrorExceptions.CustomInternalServerErrorException("An error occurred while updating the country name", e);
        }
    }

    public List<DayDto> findByCountryNameAndWeatherConditions(final String countryName, final String weatherConditions) {
        try {
            String cacheKey = countryName + "_" + weatherConditions;
            List<DayDto> cachedDays = dayCache.get(cacheKey);
            if (cachedDays != null) {
                return cachedDays;
            }
            List<Day> days = repositoryOfDay.findByCountryNameAndWeatherConditions(countryName, weatherConditions);
            List<DayDto> dayDtos = new ArrayList<>();
            for (Day day : days) {
                dayDtos.add(DayMapper.toDto(day));
            }
            dayCache.put(cacheKey, dayDtos);
            return dayDtos;
        } catch (Exception e) {
            throw new HttpErrorExceptions.CustomInternalServerErrorException("An error occurred while fetching days by country name and weather conditions", e);
        }
    }
}
