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

    @Column(name = "location")
    private String location;

    @Column(name = "coordinates")
    private String coordinates;

    @Column(name = "date")
    private LocalDate dateOfSunriseSunset;

    @Column(name = "sunrise")
    private LocalTime timeOfSunrise;

    @Column(name = "sunset")
    private LocalTime timeOfSunset;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;
}