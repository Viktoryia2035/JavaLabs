package sunposition.springday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sunposition.springday.model.Day;

import java.time.LocalDate;


public interface InMemoryDayDAO extends JpaRepository<Day, Long> {
    Day findByLocation(String location);

    Day findByCoordinates(String coordinates);

    Day findByDateOfSunriseSunset(LocalDate dateOfSunriseSunset);
}