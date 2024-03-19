package sunposition.springday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sunposition.springday.model.Day;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface InMemoryDayDAO extends JpaRepository<Day, Long> {

    Day findByLocation(String location);

    Day findByCoordinates(String coordinates);

    Day findByDateOfSunriseSunset(LocalDate dateOfSunriseSunset);

    @Query("SELECT d FROM Day d JOIN d.country c WHERE c.name = :countryName AND d.weatherConditions = :weatherConditions")
    List<Day> findByCountryNameAndWeatherConditions(String countryName, String weatherConditions);

    @Query("SELECT d FROM Day d JOIN d.country c WHERE c.capital = :capital AND d.timeOfSunrise = :timeOfSunrise")
    List<Day> findByCapitalAndTimeOfSunrise(String capital, LocalTime timeOfSunrise);
}