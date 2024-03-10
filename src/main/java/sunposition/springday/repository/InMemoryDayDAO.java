package sunposition.springday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sunposition.springday.model.Day;

import java.time.LocalDate;


public interface InMemoryDayDAO extends  JpaRepository<Day, Long> {
    Day findByLocation(String location);
    Day findByCoordinates(String coordinates);
    Day findByDateOfSunriseSunset(LocalDate dateOfSunriseSunset);
}