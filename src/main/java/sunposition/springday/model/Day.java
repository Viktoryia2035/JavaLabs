package sunposition.springday.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "day")
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String coordinates;
    private LocalDate dateOfSunriseSunset;
    private LocalTime timeOfSunrise;
    private LocalTime timeOfSunset;
}